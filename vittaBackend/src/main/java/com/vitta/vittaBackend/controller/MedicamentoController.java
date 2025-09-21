package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.dto.request.medicamento.MedicamentoDTORequest;
import com.vitta.vittaBackend.dto.request.medicamento.MedicamentoAtualizarDTORequest;
import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoDTOResponse;
import com.vitta.vittaBackend.service.MedicamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciar as operações CRUD do catálogo de medicamentos.
 * Expõe os endpoints da API REST para interagir com os recursos de Medicamento.
 */
@RestController
@RequestMapping("/api/medicamento")
@Tag(name = "Medicamento", description = "API para gerenciamento de medicamentos.")
public class MedicamentoController {

    private MedicamentoService medicamentoService;

    /**
     * Construtor para injeção de dependência do MedicamentoService.
     * @param medicamentoService O serviço que contém a lógica de negócio para medicamentos.
     */
    public MedicamentoController(MedicamentoService medicamentoService) { this.medicamentoService = medicamentoService; }

    /**
     * Lista todos os medicamentos ativos no catálogo.
     * @return ResponseEntity contendo uma lista de medicamentos ativos e o status HTTP 200 OK.
     */
    @GetMapping("/listar")
    @Operation(summary = "Listar Medicamentos", description = "Endpoint para listar todos os Medicamentos.")
    public ResponseEntity<List<MedicamentoDTOResponse>> listarMedicamentos() { return ResponseEntity.ok(medicamentoService.listarMedicamentosAtivos()); }

    /**
     * Busca um medicamento específico pelo seu ID.
     * O serviço irá lançar uma exceção se o ID não for encontrado, que será tratada
     * para retornar um status 404 Not Found.
     * @param medicamentoId O ID do medicamento a ser buscado.
     * @return ResponseEntity com o DTO do medicamento encontrado e status 200 OK.
     */
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

    /**
     * Cadastra um novo medicamento no catálogo.
     * @param medicamentoDTO O DTO contendo os dados do medicamento a ser criado.
     * @return ResponseEntity com o DTO do medicamento recém-criado e o status HTTP 201 Created.
     */
    @PostMapping("/cadastrar")
    @Operation(summary = "Criar novo Medicamento", description = "Endpoint para criar um novo registro de Medicamento.")
    public ResponseEntity<MedicamentoDTOResponse> cadastrarMedicamento(@Valid @RequestBody MedicamentoDTORequest medicamentoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoService.cadastrarMedicamento(medicamentoDTO));
    }

    /**
     * Atualiza um medicamento existente no catálogo.
     * @param medicamentoId O ID do medicamento a ser atualizado.
     * @param medicamentoDTO O DTO contendo os dados para atualização (apenas os campos que devem ser alterados).
     * @return ResponseEntity com o DTO do medicamento atualizado e o status HTTP 200 OK.
     */
    @PutMapping("/atualizar/{medicamentoId}")
    @Operation(summary = "Atualizar todos os dados do Medicamento", description = "Endpoint para atualizar o registro do Medicamento, pelo ID.")
    public ResponseEntity<MedicamentoDTOResponse> atualizarMedicamentoPorId(
            @PathVariable("medicamentoId") Integer medicamentoId,
            @RequestBody @Valid MedicamentoAtualizarDTORequest medicamentoDTO) {
        MedicamentoDTOResponse medicamentoAtualizado = medicamentoService.atualizarMedicamento(medicamentoId, medicamentoDTO);
        return ResponseEntity.ok(medicamentoAtualizado);
    }

    /**
     * Realiza a exclusão lógica de um medicamento, alterando seu status para INATIVO.
     * @param medicamentoId O ID do medicamento a ser desativado.
     * @return ResponseEntity com o status HTTP 204 No Content, indicando sucesso na operação.
     */
    @DeleteMapping("/deletar/{medicamentoId}")
    @Operation(summary = "Deletar todos os dados do Medicamento", description = "Endpoint para deletar o registro do Medicamento, pelo ID.")
    public ResponseEntity<Void> deletarMedicamento(@PathVariable("medicamentoId") Integer medicamentoId) {
        medicamentoService.deletarLogico(medicamentoId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todos os medicamentos inativos (excluídos logicamente) no catálogo.
     * @return ResponseEntity contendo uma lista de medicamentos inativos e o status HTTP 200 OK.
     */
    @GetMapping("/listar/inativos")
    @Operation(summary = "Listar Medicamentos inativos", description = "Endpoint para listar todos os Medicamentos inativos.")
    public ResponseEntity <List<MedicamentoDTOResponse>> listarMedicamentosInativos() { return ResponseEntity.ok(medicamentoService.listarMedicamentosInativos()); }
}
