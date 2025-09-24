package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.tratamento.TratamentoAtualizarDTORequest;
import com.vitta.vittaBackend.dto.request.tratamento.TratamentoDTORequest;
import com.vitta.vittaBackend.dto.response.tratamento.TratamentoDTOResponse;
import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.entity.Tratamento;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.TipoFrequencia;
import com.vitta.vittaBackend.enums.tratamento.TratamentoStatus;
import com.vitta.vittaBackend.enums.agendamento.AgendamentoStatus;
import com.vitta.vittaBackend.enums.agendamento.TipoDeAlerta;
import com.vitta.vittaBackend.repository.AgendamentoRepository;
import com.vitta.vittaBackend.repository.MedicamentoRepository;
import com.vitta.vittaBackend.repository.TratamentoRepository;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço para gerenciar a lógica de negócio dos Tratamentos.
 * Responsável por criar tratamentos e gerar os agendamentos correspondentes.
 */
@Service
public class TratamentoService {

    private final TratamentoRepository tratamentoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TratamentoService(
            TratamentoRepository tratamentoRepository,
            AgendamentoRepository agendamentoRepository,
            MedicamentoRepository medicamentoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.tratamentoRepository = tratamentoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Retorna uma lista de todos os tratamentos com status ATIVO.
     * @return Uma lista de {@link TratamentoDTOResponse}.
     */
    public List<TratamentoDTOResponse> listarTratamentos() {
        return tratamentoRepository.listarTratamentos()
                .stream()
                .map(TratamentoDTOResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um tratamento pelo seu ID.
     * @param tratamentoId O ID do tratamento a ser buscado.
     * @return O {@link TratamentoDTOResponse} correspondente ao ID.
     */
    public TratamentoDTOResponse buscarTratamentoPorId(Integer tratamentoId) {
        Tratamento tratamento = this.validarTratamento(tratamentoId);
        return new TratamentoDTOResponse(tratamento);
    }

    @Transactional
    public TratamentoDTOResponse cadastrarTratamento(TratamentoDTORequest tratamentoDTORequest) {

        // linkar com Medicamento e Usuario já cadastrados passando o ID no DTORequest
        Medicamento medicamento = medicamentoRepository.findById(tratamentoDTORequest.getMedicamentoId()).orElseThrow();
        Usuario usuario = usuarioRepository.findById(tratamentoDTORequest.getUsuarioId()).orElseThrow();

        Tratamento tratamento = new Tratamento();

        tratamento.setMedicamento(medicamento);
        tratamento.setUsuario(usuario);
        tratamento.setDosagem(tratamentoDTORequest.getDosagem());
        tratamento.setInstrucoes(tratamentoDTORequest.getInstrucoes());
        tratamento.setDataDeInicio(tratamentoDTORequest.getDataDeInicio());
        tratamento.setDataDeTermino(tratamentoDTORequest.getDataDeTermino());

        if (tratamentoDTORequest.getTipoDeFrequencia() != null) {
            tratamento.setTipoDeFrequencia(
                    TipoFrequencia.fromCodigo(tratamentoDTORequest.getTipoDeFrequencia())
            );
        }

        tratamento.setIntervaloEmHoras(tratamentoDTORequest.getIntervaloEmHoras());
        tratamento.setHorariosEspecificos(tratamentoDTORequest.getHorariosEspecificos());


        //guardar o tipoDeAlerta em uma variável local para ser usada como parâmetro em outro método
        TipoDeAlerta tipoDeAlertaEscolhido;
        if (tratamentoDTORequest.getTipoDeAlerta() != null) {
            tipoDeAlertaEscolhido = TipoDeAlerta.fromCodigo(tratamentoDTORequest.getTipoDeAlerta());
        } else {
            //se nenhum código for enviado, usa um enum padrão
            tipoDeAlertaEscolhido = TipoDeAlerta.NOTIFICACAO_PUSH;
        }

        Tratamento tratamentoSalvo = tratamentoRepository.save(tratamento);

        // gerar e salvar os agendamentos
        List<Agendamento> agendamentos = gerarAgendamentosParaTratamento(tratamentoSalvo, tipoDeAlertaEscolhido);
        if (agendamentos != null && !agendamentos.isEmpty()) {
            agendamentoRepository.saveAll(agendamentos);
        }

        return new TratamentoDTOResponse(tratamentoSalvo);
    }

    /**
     * Atualiza os dados de um tratamento existente.
     * @param tratamentoId O ID do tratamento a ser atualizado.
     * @param tratamentoAtualizarDTORequest O DTO com os novos dados.
     * @return O {@link TratamentoDTOResponse} da entidade atualizada.
     */
    @Transactional
    public TratamentoDTOResponse atualizarTratamento(Integer tratamentoId, TratamentoAtualizarDTORequest tratamentoAtualizarDTORequest) {
        Tratamento tratamentoExistente = validarTratamento(tratamentoId);

        boolean regerarAgendamentos = false;
        TipoDeAlerta tipoDeAlertaParaRegeracao = null;

        if (tratamentoAtualizarDTORequest.getDosagem() != null) {
            tratamentoExistente.setDosagem(tratamentoAtualizarDTORequest.getDosagem());
        }
        if (tratamentoAtualizarDTORequest.getInstrucoes() != null) {
            tratamentoExistente.setInstrucoes(tratamentoAtualizarDTORequest.getInstrucoes());
        }

        // fazer verificação para ver se a estrutura do agendamento mudou (datas, frequência)
        if (tratamentoAtualizarDTORequest.getDataDeInicio() != null && !tratamentoAtualizarDTORequest.getDataDeInicio().equals(tratamentoExistente.getDataDeInicio())) {
            tratamentoExistente.setDataDeInicio(tratamentoAtualizarDTORequest.getDataDeInicio());
            regerarAgendamentos = true;
        }
        if (tratamentoAtualizarDTORequest.getDataDeTermino() != null && !tratamentoAtualizarDTORequest.getDataDeTermino().equals(tratamentoExistente.getDataDeTermino())) {
            tratamentoExistente.setDataDeTermino(tratamentoAtualizarDTORequest.getDataDeTermino());
            regerarAgendamentos = true;
        }
        if (tratamentoAtualizarDTORequest.getTipoDeFrequencia() != null) {
            TipoFrequencia novaFrequencia = TipoFrequencia.fromCodigo(tratamentoAtualizarDTORequest.getTipoDeFrequencia());
            if (novaFrequencia != tratamentoExistente.getTipoDeFrequencia()) {
                tratamentoExistente.setTipoDeFrequencia(novaFrequencia);
                regerarAgendamentos = true;
            }
            // sempre atualiza os campos de frequência se o tipo for enviado
            tratamentoExistente.setIntervaloEmHoras(tratamentoAtualizarDTORequest.getIntervaloEmHoras());
            tratamentoExistente.setHorariosEspecificos(tratamentoAtualizarDTORequest.getHorariosEspecificos());
        }

        // verificar se o tipo de alerta mudou (isso também força a regeração)
        if (tratamentoAtualizarDTORequest.getTipoDeAlerta() != null) {
            tipoDeAlertaParaRegeracao = TipoDeAlerta.fromCodigo(tratamentoAtualizarDTORequest.getTipoDeAlerta());
            regerarAgendamentos = true; // se o alerta muda, os futuros agendamentos precisam ser recriados
        } else if (regerarAgendamentos) {
            // se precisa regerar, mas o alerta não foi alterado, precisamos buscar o alerta atual
            // para manter a consistência.
            tipoDeAlertaParaRegeracao = agendamentoRepository
                    .findFirstByTratamentoId(tratamentoId)
                    .map(Agendamento::getTipoDeAlerta)
                    .orElse(TipoDeAlerta.NOTIFICACAO_PUSH); // Fallback para um padrão, caso não encontre nenhum
        }

        if (regerarAgendamentos) {
            agendamentoRepository.deleteByTratamentoIdAndStatusAndHorarioDoAgendamentoAfter(
                    tratamentoId,
                    AgendamentoStatus.PENDENTE,
                    LocalDateTime.now()
            );

            List<Agendamento> novosAgendamentos = gerarAgendamentosParaTratamento(tratamentoExistente, tipoDeAlertaParaRegeracao);

            if (novosAgendamentos != null && !novosAgendamentos.isEmpty()) {
                agendamentoRepository.saveAll(novosAgendamentos);
            }
        }

        Tratamento tratamentoAtualizado = tratamentoRepository.save(tratamentoExistente);
        return new TratamentoDTOResponse(tratamentoAtualizado);
    }

    /**
     * Realiza a exclusão lógica de um tratamento, alterando seu status para CANCELADO.
     * @param tratamentoId O ID do tratamento a ser desativado.
     */
    @Transactional
    public void deletarLogico(Integer tratamentoId) {
        Tratamento tratamento = this.validarTratamento(tratamentoId);

        tratamento.setStatus(TratamentoStatus.CANCELADO);
        tratamentoRepository.save(tratamento);
    }



    /**
     * Valida a existência de um tratamento pelo seu ID e o retorna.
     * Este é um método auxiliar privado para evitar a repetição de código nos
     * métodos públicos que precisam de buscar uma entidade antes de realizar uma ação.
     *
     * @param tratamentoId o ID do tratamento a ser validado e buscado.
     * @return A entidade {@link Tratamento} encontrada.
     */
    private Tratamento validarTratamento(Integer tratamentoId) {
        Tratamento tratamento = tratamentoRepository.obterTratamentoPeloId(tratamentoId);

        if (tratamento == null) {
            throw new EntityNotFoundException("Tratamento não encontrado com o ID: " + tratamentoId);
        }

        return tratamento;
    }

    /**
     * Lógica principal para gerar a lista completa de agendamentos com base nas regras de um tratamento.
     * <p>
     * Este método itera do primeiro ao último dia do tratamento e, para cada dia, calcula
     * os horários das doses de acordo com o tipo de frequência definido (intervalo de horas
     * ou horários específicos). Ele utiliza o método auxiliar {@link #criarAgendamento}
     * para instanciar cada agendamento individual.
     *
     * @param tratamento A entidade {@link Tratamento} já salva, contendo as regras de agendamento.
     * @return Uma lista de novas entidades {@link Agendamento}, prontas para serem salvas no banco de dados.
     */
    private List<Agendamento> gerarAgendamentosParaTratamento(Tratamento tratamento, TipoDeAlerta tipoDeAlerta) {
        List<Agendamento> agendamentos = new ArrayList<>();

        if (tratamento.getDataDeTermino() == null || tratamento.getDataDeTermino().isBefore(tratamento.getDataDeInicio())) {
            return agendamentos;
        }

        LocalDate dataCorrente = tratamento.getDataDeInicio();

        while (!dataCorrente.isAfter(tratamento.getDataDeTermino())) {
            if (tratamento.getTipoDeFrequencia() == TipoFrequencia.INTERVALO_HORAS) {

                // define o primeiro horário do dia
                LocalDateTime proximoAgendamento = dataCorrente.atTime(8, 0);

                // loop para gerar agendamentos ENQUANTO eles pertencerem à dataCorrente
                while (proximoAgendamento.toLocalDate().isEqual(dataCorrente)) {
                    // adiciona o agendamento apenas se ele não ultrapassar a data e hora de término do tratamento
                    if (tratamento.getDataDeTermino() != null) {
                        LocalDateTime dataHoraTermino = tratamento.getDataDeTermino().atTime(LocalTime.MAX);
                        if (proximoAgendamento.isAfter(dataHoraTermino)) {
                            break; // para o loop se o próximo agendamento já passou do fim do tratamento
                        }
                    }

                    agendamentos.add(criarAgendamento(tratamento, proximoAgendamento, tipoDeAlerta));

                    proximoAgendamento = proximoAgendamento.plusHours(tratamento.getIntervaloEmHoras());
                }

            } else if (tratamento.getTipoDeFrequencia() == TipoFrequencia.HORARIOS_ESPECIFICOS) {
                String[] horarios = tratamento.getHorariosEspecificos().split(",");
                for (String horaStr : horarios) {
                    LocalTime horario = LocalTime.parse(horaStr.trim());
                    LocalDateTime dataHoraAgendamento = LocalDateTime.of(dataCorrente, horario);

                    // passa o tipoDeAlerta para o método criador
                    agendamentos.add(criarAgendamento(tratamento, dataHoraAgendamento, tipoDeAlerta));
                }
            }
            dataCorrente = dataCorrente.plusDays(1);
        }
        return agendamentos;
    }


    /**
     * Método auxiliar (factory) para criar e configurar uma única instância da entidade {@link Agendamento}.
     * <p>
     * Centraliza a criação do objeto, definindo os valores padrão como o status inicial {@code PENDENTE}.
     *
     * @param tratamento O tratamento "pai" ao qual este agendamento pertence.
     * @param dataHora   A data e hora exata em que este agendamento deve ocorrer.
     * @return A nova entidade {@link Agendamento}, pronta para ser adicionada à lista de geração.
     */
    private Agendamento criarAgendamento(Tratamento tratamento, LocalDateTime dataHora, TipoDeAlerta tipoDeAlerta) {
        Agendamento agendamento = new Agendamento();

        agendamento.setTratamento(tratamento);
        agendamento.setHorarioDoAgendamento(dataHora);
        agendamento.setStatus(AgendamentoStatus.PENDENTE);
        agendamento.setTipoDeAlerta(tipoDeAlerta);
        agendamento.setUsuario(tratamento.getUsuario());

        return agendamento;
    }
}
