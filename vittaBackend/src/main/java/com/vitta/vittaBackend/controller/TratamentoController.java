package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.dto.request.tratamento.TratamentoDTORequest;
import com.vitta.vittaBackend.service.TratamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tratamentos")
@Tag(name = "Tratamento", description = "API para gerenciamento de tratamentos.")
public class TratamentoController {

    private TratamentoService tratamentoService;

    public TratamentoController(TratamentoService tratamentoService) { this.tratamentoService = tratamentoService; }

    @PostMapping("/cadastrar")
    @Operation(summary = "Criar novo Tratamento", description = "Endpoint para criar um novo registro de Tratamento.")
    public ResponseEntity<Void> cadastrarTratamento(@Valid @RequestBody TratamentoDTORequest tratamentoDTORequest) { // criar o DTOResponse de tratamento para retornar nesta linha
        return ResponseEntity.status(HttpStatus.CREATED).body(tratamentoService.cadastrarTratamento(tratamentoDTORequest));
    }
}
