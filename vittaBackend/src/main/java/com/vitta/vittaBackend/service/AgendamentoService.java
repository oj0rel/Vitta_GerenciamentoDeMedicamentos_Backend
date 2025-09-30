package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.agendamento.AgendamentoAtualizarDTORequest;
import com.vitta.vittaBackend.dto.request.agendamento.AgendamentoDTORequest;
import com.vitta.vittaBackend.dto.response.agendamento.AgendamentoDTOResponse;
import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.entity.Tratamento;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.agendamento.AgendamentoStatus;
import com.vitta.vittaBackend.enums.agendamento.TipoDeAlerta;
import com.vitta.vittaBackend.repository.AgendamentoRepository;
import com.vitta.vittaBackend.repository.MedicamentoHistoricoRepository;
import com.vitta.vittaBackend.repository.TratamentoRepository;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TratamentoRepository tratamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AgendamentoService(
            AgendamentoRepository agendamentoRepository,
            UsuarioRepository usuarioRepository,
            TratamentoRepository tratamentoRepository
    ) {
        this.agendamentoRepository = agendamentoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tratamentoRepository = tratamentoRepository;
    }

    /**
     * Lista todos os agendamentos ativos de um usuário específico.
     * @param usuarioId O ID do usuário autenticado.
     * @return Uma lista de AgendamentoDTOResponse.
     */
    public List<AgendamentoDTOResponse> listarAgendamentosPorUsuario(Integer usuarioId) {
        List<Agendamento> agendamentosDoUsuario = agendamentoRepository.listarAgendamentosAtivos(usuarioId);
        return agendamentosDoUsuario.stream()
                .map(agendamento -> modelMapper.map(agendamento, AgendamentoDTOResponse.class))
                .collect(Collectors.toList());
    }

    /**
     * Busca um único agendamento pelo seu ID, garantindo que ele pertença ao usuário.
     * @param agendamentoId O ID do agendamento a ser buscado.
     * @param usuarioId O ID do usuário autenticado.
     * @return O AgendamentoDTOResponse correspondente.
     */
    public AgendamentoDTOResponse listarAgendamentoPorId(Integer agendamentoId, Integer usuarioId) {
        Agendamento agendamento = validarAgendamento(agendamentoId, usuarioId);
        return new AgendamentoDTOResponse(agendamento);
    }

    /**
     * Cadastra um novo agendamento para o usuário autenticado.
     * O ID do usuário é obtido do contexto de segurança, não do DTO.
     * @param agendamentoDTORequest DTO com os dados do novo agendamento.
     * @param usuarioId O ID do usuário autenticado.
     * @return O AgendamentoDTOResponse do novo agendamento.
     */
    @Transactional
    public AgendamentoDTOResponse cadastrarAgendamento(AgendamentoDTORequest agendamentoDTORequest, Integer usuarioId) {
        Usuario usuario = usuarioRepository.getReferenceById(usuarioId);

        Tratamento tratamento = tratamentoRepository.listarTratamentoPorId(agendamentoDTORequest.getTratamentoId(), usuarioId);

        if (tratamento == null) {
            throw new EntityNotFoundException("Tratamento não encontrado ou não pertence a este usuário.");
        }

        Agendamento agendamento = new Agendamento();

        agendamento.setHorarioDoAgendamento(agendamentoDTORequest.getHorarioDoAgendamento());

        if (agendamentoDTORequest.getTipoDeAlerta() != null) {
            agendamento.setTipoDeAlerta(
                    TipoDeAlerta.fromCodigo(agendamentoDTORequest.getTipoDeAlerta())
            );
        }

        agendamento.setTratamento(tratamento);
        agendamento.setUsuario(usuario);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        return new AgendamentoDTOResponse(agendamentoSalvo);
    }

    /**
     * Atualiza um agendamento existente, garantindo que ele pertença ao usuário.
     * @param agendamentoId O ID do agendamento a ser atualizado.
     * @param usuarioId O ID do usuário autenticado.
     * @param agendamentoAtualizarDTORequest DTO com os dados de atualização.
     * @return O AgendamentoDTOResponse atualizado.
     */
    @Transactional
    public AgendamentoDTOResponse atualizarAgendamento(Integer agendamentoId, Integer usuarioId, AgendamentoAtualizarDTORequest agendamentoAtualizarDTORequest) {
        Agendamento agendamentoBuscado = validarAgendamento(agendamentoId, usuarioId);

            agendamentoBuscado.setHorarioDoAgendamento(agendamentoAtualizarDTORequest.getHorarioDoAgendamento());

            if (agendamentoAtualizarDTORequest.getTipoDeAlerta() != null) {
                agendamentoBuscado.setTipoDeAlerta(
                        TipoDeAlerta.fromCodigo(agendamentoAtualizarDTORequest.getTipoDeAlerta())
                );
            }

            // converte o Integer que vem do AtualizarDTORequest para o Enum AgendamentoStatus
            if (agendamentoAtualizarDTORequest.getStatus() != null) {
                agendamentoBuscado.setStatus(
                        AgendamentoStatus.fromCodigo(agendamentoAtualizarDTORequest.getStatus())
                );
            }

            Agendamento agendamentoRecebido = agendamentoRepository.save(agendamentoBuscado);
            return new AgendamentoDTOResponse(agendamentoRecebido);
    }

    /**
     * Realiza a exclusão lógica de um agendamento, garantindo que ele pertença ao usuário.
     * @param agendamentoId O ID do agendamento a ser deletado.
     * @param usuarioId O ID do usuário autenticado.
     */
    @Transactional
    public void deletarAgendamento(Integer agendamentoId, Integer usuarioId) { agendamentoRepository.apagarLogicoAgendamento(agendamentoId, usuarioId); }


    /**
     * Valida a existência de um agendamento e a sua posse pelo usuário especificado.
     * <p>
     * Este é um método auxiliar privado que busca um agendamento no repositório
     * usando tanto o ID do agendamento quanto o ID do usuário. Ele serve como uma
     * verificação de segurança e existência antes de qualquer operação de atualização ou exclusão.
     *
     * @param agendamentoId O ID do agendamento a ser validado e buscado.
     * @param usuarioId O ID do usuário que deve ser o proprietário do agendamento.
     * @return A entidade {@link Agendamento} encontrada, caso seja válida e pertença ao usuário.
     * @throws RuntimeException se o agendamento não for encontrado ou não pertencer ao usuário especificado.
     */
    private Agendamento validarAgendamento(Integer agendamentoId, Integer usuarioId) {
        Agendamento agendamento = agendamentoRepository.listarAgendamentoPorId(agendamentoId, usuarioId);
        if (agendamento == null) {
            throw new RuntimeException("Agendamento não encontrado ou inativo.");
        }
        return agendamento;
    }
}
