package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.medicamentoHistorico.MedicamentoHistoricoDTORequest;
import com.vitta.vittaBackend.dto.request.medicamentoHistorico.MedicamentoHistoricoAtualizarDTORequest;
import com.vitta.vittaBackend.dto.response.medicamentoHistorico.MedicamentoHistoricoDTOResponse;
import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.entity.MedicamentoHistorico;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.repository.AgendamentoRepository;
import com.vitta.vittaBackend.repository.MedicamentoHistoricoRepository;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicamentoHistoricoService {

    private final MedicamentoHistoricoRepository medicamentoHistoricoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MedicamentoHistoricoService(
            MedicamentoHistoricoRepository medicamentoHistoricoRepository,
            AgendamentoRepository agendamentoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.medicamentoHistoricoRepository = medicamentoHistoricoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Lista o histórico de uso de medicamentos de um usuário específico.
     * @param usuarioId O ID do usuário autenticado.
     * @return Uma lista de MedicamentoHistoricoDTOResponse.
     */
    public List<MedicamentoHistoricoDTOResponse> listarMedicamentosHistoricos(Integer usuarioId) {
        return this.medicamentoHistoricoRepository.listarMedicamentosHistoricos(usuarioId)
                .stream()
                .map(MedicamentoHistoricoDTOResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um único registro de histórico pelo seu ID, garantindo que ele pertença ao usuário.
     * @param historicoId O ID do registro de histórico a ser buscado.
     * @param usuarioId O ID do usuário autenticado.
     * @return O MedicamentoHistoricoDTOResponse correspondente.
     */
    public MedicamentoHistoricoDTOResponse listarMedicamentoHistoricoPorId(Integer historicoId, Integer usuarioId) {
        MedicamentoHistorico medicamentoHistorico = validarMedicamentoHistorico(historicoId, usuarioId);
        return new MedicamentoHistoricoDTOResponse(medicamentoHistorico);
    }

    /**
     * Cadastra um novo histórico para o usuário autenticado.
     * O ID do usuário é obtido do contexto de segurança, não do DTO.
     * @param medicamentoHistoricoDTORequest DTO com os dados do novo agendamento.
     * @param usuarioId O ID do usuário autenticado.
     * @return O AgendamentoDTOResponse do novo agendamento.
     */
    @Transactional
    public MedicamentoHistoricoDTOResponse cadastrarMedicamentoHistorico(MedicamentoHistoricoDTORequest medicamentoHistoricoDTORequest, Integer usuarioId) {

        Agendamento agendamento = agendamentoRepository.listarAgendamentoPorId(
                medicamentoHistoricoDTORequest.getAgendamentoId(),
                usuarioId
        );
        if (agendamento == null) {
            throw new EntityNotFoundException("Agendamento não encontrado ou não pertence a este usuário.");
        }

        Usuario usuario = usuarioRepository.getReferenceById(usuarioId);

        MedicamentoHistorico medicamentoHistorico = new MedicamentoHistorico();
        medicamentoHistorico.setHoraDoUso(medicamentoHistoricoDTORequest.getHoraDoUso());
        medicamentoHistorico.setDoseTomada(medicamentoHistoricoDTORequest.getDoseTomada());
        medicamentoHistorico.setObservacao(medicamentoHistoricoDTORequest.getObservacao());

        medicamentoHistorico.setAgendamento(agendamento);
        medicamentoHistorico.setUsuario(usuario);

        MedicamentoHistorico medicamentoHistoricoSalvo = medicamentoHistoricoRepository.save(medicamentoHistorico);
        return new MedicamentoHistoricoDTOResponse(medicamentoHistoricoSalvo);
    }

    /**
     * Atualiza um registro de histórico existente, garantindo que ele pertença ao usuário.
     * @param historicoId O ID do registro de histórico a ser atualizado.
     * @param usuarioId O ID do usuário autenticado.
     * @param historicoDTORequestAtualizar DTO com os dados de atualização.
     * @return O MedicamentoHistoricoDTOResponse atualizado.
     */
    @Transactional
    public MedicamentoHistoricoDTOResponse atualizarMedicamentoHistorico(Integer historicoId, Integer usuarioId, MedicamentoHistoricoAtualizarDTORequest historicoDTORequestAtualizar) {
        MedicamentoHistorico medicamentoHistoricoExistente = validarMedicamentoHistorico(historicoId, usuarioId);

        if (historicoDTORequestAtualizar.getHoraDoUso() != null) {
            medicamentoHistoricoExistente.setHoraDoUso(historicoDTORequestAtualizar.getHoraDoUso());
        }
        if (historicoDTORequestAtualizar.getDoseTomada() != null) {
            medicamentoHistoricoExistente.setDoseTomada(historicoDTORequestAtualizar.getDoseTomada());
        }
        if (historicoDTORequestAtualizar.getObservacao() != null) {
            medicamentoHistoricoExistente.setObservacao(historicoDTORequestAtualizar.getObservacao());
        }

        MedicamentoHistorico medicamentoHistoricoAtualizado = medicamentoHistoricoRepository.save(medicamentoHistoricoExistente);
        return new MedicamentoHistoricoDTOResponse(medicamentoHistoricoAtualizado);
    }

    /**
     * Realiza a exclusão lógica de um registro de histórico, garantindo que ele pertença ao usuário.
     * @param historicoId O ID do registro a ser deletado.
     * @param usuarioId O ID do usuário autenticado.
     */
    @Transactional
    public void deletarMedicamentoHistorico(Integer historicoId, Integer usuarioId) { medicamentoHistoricoRepository.apagarLogicoMedicamentoHistorico(historicoId, usuarioId); }



    /**
     * Valida a existência de um registro de histórico e a sua posse pelo usuário especificado.
     * <p>
     * Este é um método auxiliar privado que busca um registro de histórico no repositório
     * usando tanto o ID do histórico quanto o ID do usuário. Ele serve como uma
     * verificação de segurança e existência antes de operações como atualização ou exclusão.
     *
     * @param medicamentoHistoricoId O ID do registro de histórico a ser validado e buscado.
     * @param usuarioId O ID do usuário que deve ser o proprietário do histórico.
     * @return A entidade {@link MedicamentoHistorico} encontrada, caso seja válida e pertença ao usuário.
     * @throws RuntimeException se o registro de histórico não for encontrado ou não pertencer ao usuário.
     */
    private MedicamentoHistorico validarMedicamentoHistorico(Integer medicamentoHistoricoId, Integer usuarioId) {
        MedicamentoHistorico medicamentoHistorico = medicamentoHistoricoRepository.listarMedicamentoHistoricoPorId(medicamentoHistoricoId, usuarioId);
        if (medicamentoHistorico == null) {
            throw new RuntimeException("Histórico do medicamento não encontrado ou inativo.");
        }
        return medicamentoHistorico;
    }

//    @Transactional
//    public AgendamentoDTOResponse registrarUsoDoMedicamento(RegistrarUsoDTORequest registroDoUso) {
//
//        Agendamento agendamento = agendamentoRepository.findById(registroDoUso.getAgendamentoId())
//                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado..."));
//
//        if (agendamento.getStatus() != AgendamentoStatus.PENDENTE) {
//            throw new IllegalStateException("Este agendamento já foi concluído ou cancelado.");
//        }
//
//        MedicamentoHistorico novoHistorico = new MedicamentoHistorico();
//        novoHistorico.setMedicamento(agendamento.getMedicamento());
//        novoHistorico.setHoraDoUso(registroDoUso.getHoraDoUso());
//        novoHistorico.setDoseTomada(registroDoUso.getDoseTomada());
//        novoHistorico.setObservacao(registroDoUso.getObservacao());
//
//        novoHistorico.setAgendamento(agendamento);
//        agendamento.setMedicamentoHistorico(novoHistorico);
//
//        agendamento.setStatus(AgendamentoStatus.TOMADO);
//
//        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
//
//        return converterAgendamentoParaDTO(agendamentoSalvo);
//    }
//
//    private AgendamentoDTOResponse converterAgendamentoParaDTO(Agendamento agendamento) {
//        AgendamentoDTOResponse dto = new AgendamentoDTOResponse();
//        dto.setId(agendamento.getId());
//        dto.setHorarioDoAgendamento(agendamento.getHorarioDoAgendamento());
//        dto.setTipoDeAlerta(agendamento.getTipoDeAlerta());
//        dto.setStatus(agendamento.getStatus());
//
//        if (agendamento.getUsuario() != null) {
//            dto.setUsuario(new UsuarioResumoDTOResponse(agendamento.getUsuario()));
//        }
//        if (agendamento.getMedicamento() != null) {
//            dto.setMedicamento(new MedicamentoResumoDTOResponse(agendamento.getMedicamento()));
//        }
//
//        if (agendamento.getMedicamentoHistorico() != null) {
//            dto.setHistoricoDoMedicamentoTomado(
//                    new MedicamentoHistoricoResumoDTOResponse(agendamento.getMedicamentoHistorico())
//            );
//        }
//
//        return dto;
//    }

}
