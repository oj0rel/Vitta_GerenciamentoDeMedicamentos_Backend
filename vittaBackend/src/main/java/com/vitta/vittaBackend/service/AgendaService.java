package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.response.agenda.AgendaDoDiaDTOResponse;
import com.vitta.vittaBackend.dto.response.medicamentoHistorico.MedicamentoHistoricoDTOResponse;
import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendaService {

//    @Autowired
//    private AgendamentoRepository agendamentoRepository;
//
//    public List<AgendaDoDiaDTOResponse> getAgendaDoDia(Integer usuarioId) {
//        //define o intervalo de hoje
//        LocalDate hoje = LocalDate.now();
//        LocalDateTime inicioDoDia = hoje.atStartOfDay(); // Ex: 2025-09-17T00:00:00
//        LocalDateTime fimDoDia = hoje.atTime(LocalTime.MAX);   // Ex: 2025-09-17T23:59:59.999...
//
//        //busca o agendamento no banco de dados
//        List<Agendamento> agendamentosDoDia = agendamentoRepository.findByUsuarioIdAndData(usuarioId, inicioDoDia, fimDoDia);
//
//        return agendamentosDoDia.stream()
//                .map(this::converterParaResponseDTO)
//                .collect(Collectors.toList());
//    }

//    private AgendaDoDiaDTOResponse converterParaResponseDTO(Agendamento agendamento) {
//        AgendaDoDiaDTOResponse agendaDoDiaDTOResponse = new AgendaDoDiaDTOResponse();
//
//        agendaDoDiaDTOResponse.setAgendamentoId(agendamento.getId());
//        agendaDoDiaDTOResponse.setHorario(agendamento.getHorarioDoAgendamento());
//        agendaDoDiaDTOResponse.setStatusDoAgendamento(agendamento.getStatus());
//
//        //acessa o objeto Medicamento relacionado para pegar os dados dele
//        agendaDoDiaDTOResponse.setNomeMedicamento(agendamento.getMedicamento().getNome());
//        agendaDoDiaDTOResponse.setDosagem(agendamento.getMedicamento().getDosagem());
//        agendaDoDiaDTOResponse.setInstrucoes(agendamento.getMedicamento().getInstrucoes());
//
//        if (agendamento.getMedicamentoHistorico() != null) {
//            agendaDoDiaDTOResponse.setHistorico(new MedicamentoHistoricoDTOResponse(agendamento.getMedicamentoHistorico()));
//        }
//
//        return agendaDoDiaDTOResponse;
//    }
}
