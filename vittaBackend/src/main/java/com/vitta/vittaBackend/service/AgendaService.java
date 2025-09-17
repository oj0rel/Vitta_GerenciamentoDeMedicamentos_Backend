package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.response.agenda.AgendaDoDiaDTOResponse;
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

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public List<AgendaDoDiaDTOResponse> getAgendaDoDia(int usuarioId) {
        // 1. Definir o intervalo de hoje
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicioDoDia = hoje.atStartOfDay(); // Ex: 2025-09-17T00:00:00
        LocalDateTime fimDoDia = hoje.atTime(LocalTime.MAX);   // Ex: 2025-09-17T23:59:59.999...

        // 2. Buscar os agendamentos no banco de dados
        List<Agendamento> agendamentosDoDia = agendamentoRepository.findByUsuarioIdAndData(usuarioId, inicioDoDia, fimDoDia);

        // 3. Converter a lista de Entidades para uma lista de DTOs
        return agendamentosDoDia.stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }

    // O método auxiliar agora cria e retorna um objeto da sua classe DTO
    private AgendaDoDiaDTOResponse converterParaResponseDTO(Agendamento agendamento) {
        // Criamos uma instância do seu DTO
        AgendaDoDiaDTOResponse agendaDoDiaDTOResponse = new AgendaDoDiaDTOResponse();

        // Mapeamos cada campo da entidade para o DTO
        agendaDoDiaDTOResponse.setAgendamentoId(agendamento.getId());
        agendaDoDiaDTOResponse.setHorario(agendamento.getHorarioDoAgendamento());
        agendaDoDiaDTOResponse.setStatusDoAgendamento(agendamento.getStatus());

        // Acessamos o objeto Medicamento relacionado para pegar os dados dele
        agendaDoDiaDTOResponse.setNomeMedicamento(agendamento.getMedicamento().getNome());
        agendaDoDiaDTOResponse.setDosagem(agendamento.getMedicamento().getDosagem());
        agendaDoDiaDTOResponse.setInstrucoes(agendamento.getMedicamento().getInstrucoes());

        return agendaDoDiaDTOResponse;
    }
}
