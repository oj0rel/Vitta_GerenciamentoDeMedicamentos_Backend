package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.dto.request.tratamento.TratamentoAtualizarDTORequest;
import com.vitta.vittaBackend.dto.request.tratamento.TratamentoDTORequest;
import com.vitta.vittaBackend.dto.response.tratamento.TratamentoDTOResponse;
import com.vitta.vittaBackend.service.TratamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tratamento")
@Tag(name = "Tratamento", description = "API para gerenciamento de tratamentos.")
public class TratamentoController {

    private TratamentoService tratamentoService;

    public TratamentoController(TratamentoService tratamentoService) { this.tratamentoService = tratamentoService; }

    /**
     * Lista todos os tratamentos com status ATIVO.
     * @return ResponseEntity contendo uma lista de tratamentos e o status HTTP 200 OK.
     */
    @GetMapping("/listar")
    @Operation(summary = "Listar Tratamentos", description = "Endpoint para listar todos os Tratamentos.")
    public ResponseEntity <List<TratamentoDTOResponse>> listarTratamentos() { return ResponseEntity.ok(tratamentoService.listarTratamentos()); }

    /**
     * Busca um tratamento ativo específico pelo seu ID.
     * @param tratamentoId O ID do tratamento a ser buscado.
     * @return ResponseEntity com o DTO do tratamento encontrado e status 200 OK.
     * Retorna 404 Not Found se o tratamento não existir ou estiver inativo.
     */
    @GetMapping("/listarTratamentoPorId/{tratamentoId}")
    @Operation(summary = "Listar Tratamento pelo ID dele", description = "Endpoint para listar um Tratamento, pelo ID.")
    public ResponseEntity<TratamentoDTOResponse> buscarTratamentoPorId(@PathVariable("tratamentoId") Integer tratamentoId) {
        TratamentoDTOResponse tratamentoDTOResponse = tratamentoService.buscarTratamentoPorId(tratamentoId);

        if (tratamentoId == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(tratamentoDTOResponse);
        }
    }

    /**
     * Cadastra um novo tratamento no sistema.
     * @param tratamentoDTORequest DTO contendo os dados do novo tratamento.
     * @return ResponseEntity com o DTO do tratamento recém-criado e o status HTTP 201 Created.
     */
    @PostMapping("/cadastrar")
    @Operation(summary = "Criar novo Tratamento", description = "Endpoint para criar um novo registro de Tratamento.")
    public ResponseEntity<TratamentoDTOResponse> cadastrarTratamento(@Valid @RequestBody TratamentoDTORequest tratamentoDTORequest) { // criar o DTOResponse de tratamento para retornar nesta linha
        return ResponseEntity.status(HttpStatus.CREATED).body(tratamentoService.cadastrarTratamento(tratamentoDTORequest));
    }

    /**
     * Atualiza dados de um tratamento existente.
     * @param tratamentoId O ID do usuário a ser atualizado.
     * @param tratamentoAtualizarDTORequest DTO contendo os dados a serem alterados.
     * @return ResponseEntity com o DTO do tratamento atualizado e o status HTTP 200 OK.
     */
    @PutMapping("/atualizar/{tratamentoId}")
    @Operation(summary = "Atualizar todos os dados do Tratamento", description = "Endpoint para atualizar o registro do Tratamento, pelo ID.")
    public ResponseEntity<TratamentoDTOResponse> atualizarTratamentoPorId(
            @PathVariable("tratamentoId") Integer tratamentoId,
            @RequestBody @Valid TratamentoAtualizarDTORequest tratamentoAtualizarDTORequest) {
        TratamentoDTOResponse tratamentoAtualizado = tratamentoService.atualizarTratamento(tratamentoId, tratamentoAtualizarDTORequest);
        return ResponseEntity.ok(tratamentoAtualizado);
    }

    /**
     * Realiza a exclusão lógica de um tratamento, alterando seu status para CANCELADO.
     * @param tratamentoId O ID do tratamento a ser desativado.
     * @return ResponseEntity com o status HTTP 204 No Content, indicando sucesso.
     */
    @DeleteMapping("/deletar/{tratamentoId}")
    @Operation(summary = "Deletar todos os dados do Tratamento", description = "Endpoint para deletar o registro do Tratamento, pelo ID.")
    public ResponseEntity<Void> deletarTratamento(@PathVariable("tratamentoId") Integer tratamentoId) {
        tratamentoService.deletarLogico(tratamentoId);
        return ResponseEntity.noContent().build();
    }
}
