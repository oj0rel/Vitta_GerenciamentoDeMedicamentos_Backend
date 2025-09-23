package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.tratamento.TratamentoAtualizarDTORequest;
import com.vitta.vittaBackend.dto.request.tratamento.TratamentoDTORequest;
import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoDTOResponse;
import com.vitta.vittaBackend.dto.response.tratamento.TratamentoDTOResponse;
import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.entity.Tratamento;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.TipoFrequencia;
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
    public Tratamento cadastrarTratamento(TratamentoDTORequest tratamentoDTORequest) {

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

        Tratamento tratamentoSalvo = tratamentoRepository.save(tratamento);

        // gerar e salvar os agendamentos
        List<Agendamento> agendamentos = gerarAgendamentosParaTratamento(tratamentoSalvo);
        agendamentoRepository.saveAll(agendamentos);

        return tratamentoSalvo;
    }

    @Transactional
    public TratamentoDTOResponse atualizarTratamento(Integer tratamentoId, TratamentoAtualizarDTORequest tratamentoAtualizarDTORequest) {
        Tratamento tratamentoExistente = validarTratamento(tratamentoId);

        //flag para saber se será necessário regerar os agendamentos
        boolean regerarAgendamentos = false;

        if (tratamentoAtualizarDTORequest.getDosagem() != null) {
            tratamentoExistente.setDosagem(tratamentoAtualizarDTORequest.getDosagem());
        }

        if (tratamentoAtualizarDTORequest.getInstrucoes() != null) {
            tratamentoExistente.setInstrucoes(tratamentoAtualizarDTORequest.getInstrucoes());
        }

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
            tratamentoExistente.setIntervaloEmHoras(tratamentoAtualizarDTORequest.getIntervaloEmHoras());
            tratamentoExistente.setHorariosEspecificos(tratamentoAtualizarDTORequest.getHorariosEspecificos());
        }

        if (regerarAgendamentos) {
            agendamentoRepository.deleteByTratamentoIdAndStatusAndHorarioDoAgendamentoAfter(
                    tratamentoId,
                    AgendamentoStatus.PENDENTE,
                    LocalDateTime.now()
            );

            List<Agendamento> novosAgendamentos = gerarAgendamentosParaTratamento(tratamentoExistente);

            tratamentoExistente.getAgendamentos().clear();
            tratamentoExistente.getAgendamentos().addAll(novosAgendamentos);
        }

        Tratamento tratamentoAtualizado = tratamentoRepository.save(tratamentoExistente);

        return new TratamentoDTOResponse(tratamentoAtualizado);
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

    private List<Agendamento> gerarAgendamentosParaTratamento(Tratamento tratamento) {
        List<Agendamento> agendamentos = new ArrayList<>();
        LocalDate dataCorrente = tratamento.getDataDeInicio();

        while (!dataCorrente.isAfter(tratamento.getDataDeTermino())) {
            if (tratamento.getTipoDeFrequencia() == TipoFrequencia.INTERVALO_HORAS) {
                // lógica para intervalo de horas (ex: a cada 8 horas, começando às 8h)
                LocalTime proximoHorario = LocalTime.of(8, 0); // horário de início padrão
                while (proximoHorario.isBefore(LocalTime.MAX)) {
                    LocalDateTime dataHoraAgendamento = LocalDateTime.of(dataCorrente, proximoHorario);
                    agendamentos.add(criarAgendamento(tratamento, dataHoraAgendamento));
                    proximoHorario = proximoHorario.plusHours(tratamento.getIntervaloEmHoras());
                }
            } else if (tratamento.getTipoDeFrequencia() == TipoFrequencia.HORARIOS_ESPECIFICOS) {
                // lógica para horários específicos (ex: "08:00,14:00,22:00")
                String[] horarios = tratamento.getHorariosEspecificos().split(",");
                for (String horaStr : horarios) {
                    LocalTime horario = LocalTime.parse(horaStr.trim());
                    LocalDateTime dataHoraAgendamento = LocalDateTime.of(dataCorrente, horario);
                    agendamentos.add(criarAgendamento(tratamento, dataHoraAgendamento));
                }
            }
            dataCorrente = dataCorrente.plusDays(1);
        }
        return agendamentos;
    }

    private Agendamento criarAgendamento(Tratamento tratamento, LocalDateTime dataHora) {
        Agendamento agendamento = new Agendamento();
        agendamento.setTratamento(tratamento);
        agendamento.setHorarioDoAgendamento(dataHora);
        agendamento.setStatus(AgendamentoStatus.PENDENTE);

        return agendamento;
    }
}
