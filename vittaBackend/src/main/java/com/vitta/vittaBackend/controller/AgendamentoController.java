package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.dto.request.agendamento.AgendamentoAtualizarDTORequest;
import com.vitta.vittaBackend.dto.request.agendamento.AgendamentoDTORequest;
import com.vitta.vittaBackend.dto.request.medicamentoHistorico.MedicamentoHistoricoDTORequest;
import com.vitta.vittaBackend.dto.request.medicamentoHistorico.MedicamentoHistoricoDTORequestAtualizar;
import com.vitta.vittaBackend.dto.response.agendamento.AgendamentoDTOResponse;
import com.vitta.vittaBackend.dto.response.medicamentoHistorico.MedicamentoHistoricoDTOResponse;
import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.service.AgendamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendamento")
@Tag(name = "Agendamento", description = "API para gerenciamento dos agendamentos.")
public class AgendamentoController {

    private AgendamentoService agendamentoService;

    public AgendamentoController(AgendamentoService agendamentoService) { this.agendamentoService = agendamentoService; }

    @GetMapping("/listar")
    @Operation(summary = "Listar os Agendamentos", description = "Endpoint para Listar os Agendamentos.")
    public ResponseEntity<List<AgendamentoDTOResponse>> listarAgendamentos() { return ResponseEntity.ok(agendamentoService.listarAgendamentos()); }

    @GetMapping("/listarAgendamentoPorId/{agendamentoId}")
    @Operation(summary = "Listar o Agendamento pelo ID dele", description = "Endpoint para listar um Agendamento, pelo ID.")
    public ResponseEntity<AgendamentoDTOResponse> buscarAgendamentoPorId(@PathVariable("agendamentoId") Integer agendamentoId) {
        AgendamentoDTOResponse agendamentoDTOResponse = agendamentoService.buscarAgendamentoPorId(agendamentoId);

        if (agendamentoId == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(agendamentoDTOResponse);
        }
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Criar novo Agendamento", description = "Endpoint para criar um novo registro de Agendamento.")
    public ResponseEntity<AgendamentoDTOResponse> cadastrarAgendamento(@Valid @RequestBody AgendamentoDTORequest agendamentoDTORequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoService.cadastrarAgendamento(agendamentoDTORequest));
    }

    @PutMapping("/atualizar/{agendamentoId}")
    @Operation(summary = "Atualizar todos os dados do Agendamento", description = "Endpoint para atualizar o registro do Agendamento, pelo ID.")
    public ResponseEntity<AgendamentoDTOResponse> atualizarAgendamentoPorId(
            @PathVariable("agendamentoId") Integer agendamentoId,
            @RequestBody @Valid AgendamentoAtualizarDTORequest agendamentoAtualizarDTORequest) {
        AgendamentoDTOResponse agendamentoAtualizado = agendamentoService.atualizarAgendamento(agendamentoId, agendamentoAtualizarDTORequest);
        return ResponseEntity.ok(agendamentoAtualizado);
    }

    @DeleteMapping("/deletar/{agendamentoId}")
    @Operation(summary = "Deletar todos os dados do Agendamento", description = "Endpoint para deletar o registro do Agendamento, pelo ID.")
    public ResponseEntity<Void> deletarAgendamento(@PathVariable("agendamentoId") Integer agendamentoId) {
        agendamentoService.deletarAgendamento(agendamentoId);
        return ResponseEntity.noContent().build();
    }
}
