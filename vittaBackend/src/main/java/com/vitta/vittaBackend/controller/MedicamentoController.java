package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.dto.request.medicamento.MedicamentoDTORequest;
import com.vitta.vittaBackend.dto.request.medicamento.MedicamentoDTORequestAtualizar;
import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoDTOResponse;
import com.vitta.vittaBackend.service.MedicamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamento")
@Tag(name = "Medicamento", description = "API para gerenciamento de medicamentos.")
public class MedicamentoController {

    private MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) { this.medicamentoService = medicamentoService; }

    @GetMapping("/listar")
    @Operation(summary = "Listar Medicamentos", description = "Endpoint para listar todos os Medicamentos.")
    public ResponseEntity<List<MedicamentoDTOResponse>> listarMedicamentos() { return ResponseEntity.ok(medicamentoService.listarMedicamentos()); }

    @GetMapping("/listarMedicamentoPorId/{medicamentoId}")
    @Operation(summary = "Listar Medicamento pelo ID dele", description = "Endpoint para listar um Medicamento, pelo ID.")
    public ResponseEntity<MedicamentoDTOResponse> buscarMedicamentoPorId(@PathVariable("medicamentoId") Integer medicamentoId) {
        MedicamentoDTOResponse medicamentoDTOResponse = medicamentoService.buscarMedicamentoPorId(medicamentoId);

        if (medicamentoId == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(medicamentoDTOResponse);
        }
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Criar novo Medicamento", description = "Endpoint para criar um novo registro de Medicamento.")
    public ResponseEntity<MedicamentoDTOResponse> cadastrarMedicamento(@Valid @RequestBody MedicamentoDTORequest medicamentoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoService.cadastrarMedicamento(medicamentoDTO));
    }

    @PutMapping("/atualizar/{medicamentoId}")
    @Operation(summary = "Atualizar todos os dados do Medicamento", description = "Endpoint para atualizar o registro do Medicamento, pelo ID.")
    public ResponseEntity<MedicamentoDTOResponse> atualizarMedicamentoPorId(
            @PathVariable("medicamentoId") Integer medicamentoId,
            @RequestBody @Valid MedicamentoDTORequestAtualizar medicamentoDTO) {
        MedicamentoDTOResponse medicamentoAtualizado = medicamentoService.atualizarMedicamentoPorId(medicamentoId, medicamentoDTO);
        return ResponseEntity.ok(medicamentoAtualizado);
    }

    @DeleteMapping("/deletar/{medicamentoId}")
    @Operation(summary = "Deletar todos os dados do Medicamento", description = "Endpoint para deletar o registro do Medicamento, pelo ID.")
    public ResponseEntity<Void> deletarMedicamento(@PathVariable("medicamentoId") Integer medicamentoId) {
        medicamentoService.deletarMedicamento(medicamentoId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/listar/inativos")
    @Operation(summary = "Listar Medicamentos inativos", description = "Endpoint para listar todos os Medicamentos inativos.")
    public ResponseEntity <List<MedicamentoDTOResponse>> listarMedicamentosInativos() { return ResponseEntity.ok(medicamentoService.listarMedicamentosInativos()); }
}
