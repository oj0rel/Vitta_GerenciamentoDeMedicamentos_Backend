package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.dto.request.medicamentoHistorico.MedicamentoHistoricoDTORequest;
import com.vitta.vittaBackend.dto.request.medicamentoHistorico.MedicamentoHistoricoDTORequestAtualizar;
import com.vitta.vittaBackend.dto.request.medicamentoHistorico.RegistrarUsoDTORequest;
import com.vitta.vittaBackend.dto.response.agendamento.AgendamentoDTOResponse;
import com.vitta.vittaBackend.dto.response.medicamentoHistorico.MedicamentoHistoricoDTOResponse;
import com.vitta.vittaBackend.service.MedicamentoHistoricoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentoHistorico")
@Tag(name = "Uso de Medicamento Historico", description = "API para gerenciamento dos históricos de uso de mendicamentos.")
public class MedicamentoHistoricoController {

    private MedicamentoHistoricoService medicamentoHistoricoService;

    public MedicamentoHistoricoController(MedicamentoHistoricoService medicamentoHistoricoService) { this.medicamentoHistoricoService = medicamentoHistoricoService; }

    @GetMapping("/listar")
    @Operation(summary = "Listar os Históricos de Uso de Medicamentos", description = "Endpoint para Listar os Históricos de Uso de Medicamentos.")
    public ResponseEntity<List<MedicamentoHistoricoDTOResponse>> listarMedicamentosHistoricos() { return ResponseEntity.ok(medicamentoHistoricoService.listarMedicamentosHistoricos()); }

    @GetMapping("/listarMedicamentoHistoricoPorId/{medicamentoHistoricoId}")
    @Operation(summary = "Listar o Histórico de Uso de Medicamento pelo ID dele", description = "Endpoint para listar um Histórico de Uso de Medicamento, pelo ID.")
    public ResponseEntity<MedicamentoHistoricoDTOResponse> buscarMedicamentoHistoricoPorId(@PathVariable("medicamentoHistoricoId") Integer medicamentoHistoricoId) {
        MedicamentoHistoricoDTOResponse medicamentoHistoricoDTOResponse = medicamentoHistoricoService.buscarMedicamentoHistoricoPorId(medicamentoHistoricoId);

        if (medicamentoHistoricoId == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(medicamentoHistoricoDTOResponse);
        }
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Criar novo Histórico de Uso de Medicamento", description = "Endpoint para criar um novo registro de Histórico de Uso de Medicamento.")
    public ResponseEntity<MedicamentoHistoricoDTOResponse> cadastrarMedicamentoHistorico(@Valid @RequestBody MedicamentoHistoricoDTORequest medicamentoHistoricoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoHistoricoService.cadastrarMedicamentoHistorico(medicamentoHistoricoDTO));
    }

    @PutMapping("/atualizar/{medicamentoHistoricoId}")
    @Operation(summary = "Atualizar todos os dados do Histórico de Uso de Medicamento", description = "Endpoint para atualizar o registro do Histórico de Uso de Medicamento, pelo ID.")
    public ResponseEntity<MedicamentoHistoricoDTOResponse> atualizarMedicamentoHistoricoPorId(
            @PathVariable("medicamentoHistoricoId") Integer medicamentoHistoricoId,
            @RequestBody @Valid MedicamentoHistoricoDTORequestAtualizar medicamentoHistoricoDTO) {
        MedicamentoHistoricoDTOResponse medicamentoHistoricoAtualizado = medicamentoHistoricoService.atualizarMedicamentoHistorico(medicamentoHistoricoId, medicamentoHistoricoDTO);
        return ResponseEntity.ok(medicamentoHistoricoAtualizado);
    }

    @DeleteMapping("/deletar/{medicamentoHistoricoId}")
    @Operation(summary = "Deletar todos os dados do Histórico de Uso de Medicamento", description = "Endpoint para deletar o registro do Histórico de Uso de Medicamento, pelo ID.")
    public ResponseEntity<Void> deletarMedicamentoHistorico(@PathVariable("medicamentoHistoricoId") Integer medicamentoHistoricoId) {
        medicamentoHistoricoService.deletarMedicamentoHistorico(medicamentoHistoricoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/registrarUso")
    public ResponseEntity<AgendamentoDTOResponse> registrarUso(
            @Valid @RequestBody RegistrarUsoDTORequest requestDTO) {

        AgendamentoDTOResponse respostaDTO = medicamentoHistoricoService.registrarUsoDoMedicamento(requestDTO);

        return ResponseEntity.ok(respostaDTO);
    }
}
