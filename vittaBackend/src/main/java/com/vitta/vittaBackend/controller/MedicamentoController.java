package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.dto.request.medicamento.MedicamentoDTORequest;
import com.vitta.vittaBackend.dto.request.medicamento.MedicamentoAtualizarDTORequest;
import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoDTOResponse;
import com.vitta.vittaBackend.security.UserDetailsImpl;
import com.vitta.vittaBackend.service.MedicamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar as operações de Medicamentos do usuário autenticado.
 * <p>
 * Expõe os endpoints protegidos para listar, criar, atualizar e deletar
 * medicamentos, garantindo que um usuário só possa manipular seus próprios dados
 * através da validação do token JWT em cada requisição.
 */
@RestController
@RequestMapping("/api/medicamentos")
@Tag(name = "Medicamento", description = "API para gerenciamento de medicamentos do usuário autenticado.")
public class MedicamentoController {

    private MedicamentoService medicamentoService;

    /**
     * Construtor para injeção de dependência do MedicamentoService.
     * @param medicamentoService O serviço que contém a lógica de negócio para medicamentos.
     */
    public MedicamentoController(MedicamentoService medicamentoService) { this.medicamentoService = medicamentoService; }

    /**
     * Lista todos os medicamentos do usuário autenticado.
     * @param userDetails O principal do usuário autenticado, injetado pelo Spring Security.
     * @return Uma lista de medicamentos do usuário.
     */
    @GetMapping("/listar")
    @Operation(summary = "Listar meus Medicamentos", description = "Endpoint para listar todos os Medicamentos do usuário logado.")
    public ResponseEntity<List<MedicamentoDTOResponse>> listarMedicamentos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Integer usuarioId = userDetails.getUserId();
        return ResponseEntity.ok(medicamentoService.listarMedicamentosPorUsuario(usuarioId));
    }

    /**
     * Busca um medicamento específico pelo seu ID, garantindo que pertença ao usuário autenticado.
     * @param medicamentoId O ID do medicamento a ser buscado.
     * @param userDetails O principal do usuário autenticado.
     * @return o medicamento encontrado.
     */
    @GetMapping("/listarMedicamentoPorId/{medicamentoId}")
    @Operation(summary = "Listar o Medicamento pelo ID dele", description = "Endpoint para listar um Medicamento específico do usuário logado.")
    public ResponseEntity<MedicamentoDTOResponse> buscarMedicamentoPorId(
            @PathVariable("medicamentoId") Integer medicamentoId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Integer usuarioId = userDetails.getUserId();
        MedicamentoDTOResponse medicamentoDTOResponse = medicamentoService.listarMedicamentoPorId(medicamentoId, usuarioId);

            return ResponseEntity.ok(medicamentoDTOResponse);
    }

    /**
     * Cria um novo medicamento para o usuário autenticado.
     * @param medicamentoDTORequest O DTO contendo os dados do medicamento a ser criado.
     * @param userDetails O principal do usuário autenticado.
     * @return O medicamento recém-criado.
     */
    @PostMapping("/cadastrar")
    @Operation(summary = "Criar novo Medicamento", description = "Endpoint para criar um novo registro de Medicamento para o usuário logado.")
    public ResponseEntity<MedicamentoDTOResponse> cadastrarMedicamento(
            @Valid @RequestBody MedicamentoDTORequest medicamentoDTORequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Integer usuarioId = userDetails.getUserId();
        MedicamentoDTOResponse medicamentoDTOResponse = medicamentoService.cadastrarMedicamento(medicamentoDTORequest, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoDTOResponse);
    }

    /**
     * Atualiza um medicamento existente do usuário autenticado.
     * @param medicamentoId O ID do medicamento a ser atualizado.
     * @param medicamentoAtualizarDTORequest O DTO contendo os dados para atualização (apenas os campos que devem ser alterados).
     * @param userDetails O principal do usuário autenticado.
     * @return O medicamento autalizado.
     */
    @PutMapping("/atualizar/{medicamentoId}")
    @Operation(summary = "Atualizar todos os dados do Medicamento", description = "Endpoint para atualizar o registro do Medicamento existente do usuário logado.")
    public ResponseEntity<MedicamentoDTOResponse> atualizarMedicamentoPorId(
            @PathVariable("medicamentoId") Integer medicamentoId,
            @RequestBody @Valid MedicamentoAtualizarDTORequest medicamentoAtualizarDTORequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Integer usuarioId = userDetails.getUserId();
        MedicamentoDTOResponse medicamentoAtualizado = medicamentoService.atualizarMedicamento(medicamentoId, usuarioId, medicamentoAtualizarDTORequest);
        return ResponseEntity.ok(medicamentoAtualizado);
    }

    /**
     * Realiza a exclusão lógica de um medicamento do usuário autenticado.
     * @param medicamentoId O ID do medicamento a ser desativado.
     * @param userDetails O principal do usuário autenticado.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/deletar/{medicamentoId}")
    @Operation(summary = "Deletar todos os dados do Medicamento", description = "Endpoint para deletar o registro do Medicamento do usuário logado.")
    public ResponseEntity<Void> deletarMedicamento(
            @PathVariable("medicamentoId") Integer medicamentoId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Integer usuarioId = userDetails.getUserId();
        medicamentoService.deletarLogico(medicamentoId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todos os medicamentos inativos (excluídos logicamente) de um usuário autenticado.
     * @param userDetails O principal do usuário autenticado.
     * @return ResponseEntity contendo uma lista de medicamentos inativos e o status HTTP 200 OK.
     */
    @GetMapping("/listar/inativos")
    @Operation(summary = "Listar Medicamentos inativos", description = "Endpoint para listar todos os Medicamentos inativos do usuário logado.")
    public ResponseEntity <List<MedicamentoDTOResponse>> listarMedicamentosInativos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Integer usuarioId = userDetails.getUserId();
        return ResponseEntity.ok(medicamentoService.listarMedicamentosInativos(usuarioId));
    }
}
