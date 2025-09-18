package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.medicamentoHistorico.MedicamentoHistoricoDTORequest;
import com.vitta.vittaBackend.dto.request.medicamentoHistorico.MedicamentoHistoricoDTORequestAtualizar;
import com.vitta.vittaBackend.dto.request.medicamentoHistorico.RegistrarUsoDTORequest;
import com.vitta.vittaBackend.dto.response.medicamentoHistorico.MedicamentoHistoricoDTOResponse;
import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.entity.MedicamentoHistorico;
import com.vitta.vittaBackend.enums.agendamento.AgendamentoStatus;
import com.vitta.vittaBackend.repository.AgendamentoRepository;
import com.vitta.vittaBackend.repository.MedicamentoHistoricoRepository;
import com.vitta.vittaBackend.repository.MedicamentoRepository;
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
    private final MedicamentoRepository medicamentoRepository;
    private final AgendamentoRepository agendamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public MedicamentoHistoricoService(MedicamentoHistoricoRepository medicamentoHistoricoRepository,
                                       MedicamentoRepository medicamentoRepository,
                                       AgendamentoRepository agendamentoRepository) {
        this.medicamentoHistoricoRepository = medicamentoHistoricoRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.agendamentoRepository = agendamentoRepository;
    }

    //LISTAR O HISTORICO DE USO DE MEDICAMENTOS
    public List<MedicamentoHistoricoDTOResponse> listarMedicamentosHistoricos() {
        return this.medicamentoHistoricoRepository.listarMedicamentosHistoricos()
                .stream()
                .map(medicamentoHistorico -> modelMapper.map(medicamentoHistorico, MedicamentoHistoricoDTOResponse.class))
                .collect(Collectors.toList());
    }

    //LISTAR 1 HISTORICO DE USO DE MEDICAMENTO, PEGANDO PELO ID
    public MedicamentoHistoricoDTOResponse buscarMedicamentoHistoricoPorId(Integer medicamentoHistoricoId) {
        MedicamentoHistorico medicamentoHistorico = medicamentoHistoricoRepository.obterMedicamentoHistoricoPeloId(medicamentoHistoricoId);

        return modelMapper.map(medicamentoHistorico, MedicamentoHistoricoDTOResponse.class);
    }

    //CADASTRAR HISTORICO DE USO DE MEDICAMENTO
    @Transactional
    public MedicamentoHistoricoDTOResponse cadastrarMedicamentoHistorico(MedicamentoHistoricoDTORequest medicamentoHistoricoDTORequest) {
        MedicamentoHistorico medicamentoHistorico = new MedicamentoHistorico();

        medicamentoHistorico.setHoraDoUso(medicamentoHistoricoDTORequest.getHoraDoUso());
        medicamentoHistorico.setDoseTomada(medicamentoHistoricoDTORequest.getDoseTomada());
        medicamentoHistorico.setObservacao(medicamentoHistoricoDTORequest.getObservacao());

        if (medicamentoHistoricoDTORequest.getMedicamentoId() != null) {
            Medicamento medicamento = medicamentoRepository.findById(medicamentoHistoricoDTORequest.getMedicamentoId())
                    .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));

            medicamentoHistorico.setMedicamento(medicamento);

            MedicamentoHistorico medicamentoHistoricoSalvo = medicamentoHistoricoRepository.save(medicamentoHistorico);

            return modelMapper.map(medicamentoHistoricoSalvo, MedicamentoHistoricoDTOResponse.class);
        } else {
            MedicamentoHistorico medicamentoHistoricoSalvo = medicamentoHistoricoRepository.save(medicamentoHistorico);
            return modelMapper.map(medicamentoHistoricoSalvo, MedicamentoHistoricoDTOResponse.class);
        }
    }

    //ATUALIZAR 1 HISTORICO DE USO DE MEDICAMENTO, PEGANDO PELO ID
    @Transactional
    public MedicamentoHistoricoDTOResponse atualizarMedicamentoHistorico(Integer medicamentoHistoricoId, MedicamentoHistoricoDTORequestAtualizar medicamentoHistoricoDTORequestAtualizar) {
        MedicamentoHistorico medicamentoHistoricoBuscado = this.validarMedicamentoHistorico(medicamentoHistoricoId);

        if (medicamentoHistoricoBuscado != null) {
            modelMapper.map(medicamentoHistoricoDTORequestAtualizar, medicamentoHistoricoBuscado);
            MedicamentoHistorico medicamentoHistoricoRecebido = medicamentoHistoricoRepository.save(medicamentoHistoricoBuscado);
            return modelMapper.map(medicamentoHistoricoRecebido, MedicamentoHistoricoDTOResponse.class);
        } else {
            return null;
        }
    }

    //DELETAR 1 HISTORICO DE USO DE MEDICAMENTO, PEGANDO PELO ID
    @Transactional
    public void deletarMedicamentoHistorico(Integer medicamentoHistoricoId) { medicamentoHistoricoRepository.apagadoLogicoMedicamentoHistorico(medicamentoHistoricoId); }



    //METODO PRIVADO PARA VALIDAR SE O HISTORICO DE USO EXISTE, PEGANDO PELO ID - para utilizar em outros métodos
    private MedicamentoHistorico validarMedicamentoHistorico(Integer medicamentoHistoricoId) {
        MedicamentoHistorico medicamentoHistorico = medicamentoHistoricoRepository.obterMedicamentoHistoricoPeloId(medicamentoHistoricoId);
        if (medicamentoHistorico == null) {
            throw new RuntimeException("Histórico do medicamento não encontrado ou inativo.");
        }
        return medicamentoHistorico;
    }

    @Transactional // Garante que toda a operação seja atômica
    public void registrarUsoDoMedicamento(RegistrarUsoDTORequest registroDoUso) {
        // 1. Encontrar o agendamento que o usuário está confirmando.
        Agendamento agendamento = agendamentoRepository.findById(registroDoUso.getAgendamentoId())
                .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado com o ID: " + registroDoUso.getAgendamentoId()));

        // 2. [Regra de negócio] Verificar se o agendamento já não foi concluído.
        if (agendamento.getStatus() != AgendamentoStatus.PENDENTE) {
            throw new IllegalStateException("Este agendamento já foi concluído ou cancelado.");
        }

        // 3. Atualizar o status do agendamento para TOMADO.
        agendamento.setStatus(AgendamentoStatus.TOMADO); // Supondo que você tenha um Enum TOMADO
        agendamentoRepository.save(agendamento);

        // 4. Criar o novo registro no histórico.
        MedicamentoHistorico novoHistorico = new MedicamentoHistorico();

        novoHistorico.setMedicamento(agendamento.getMedicamento()); // Pega o medicamento do agendamento
        novoHistorico.setHoraDoUso(registroDoUso.getHoraDoUso());
        novoHistorico.setDoseTomada(registroDoUso.getDoseTomada());
        novoHistorico.setObservacao(registroDoUso.getObservacao());

        novoHistorico.setAgendamento(agendamento);

        medicamentoHistoricoRepository.save(novoHistorico);
    }

}
