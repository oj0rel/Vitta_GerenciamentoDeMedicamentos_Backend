package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.dto.request.UsuarioDTORequest;
import com.vitta.vittaBackend.dto.response.UsuarioDTOResponse;
import com.vitta.vittaBackend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuário", description = "API para gerenciamento de usuários.")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) { this.usuarioService = usuarioService; }

    @GetMapping("/listar")
    @Operation(summary = "Listar usuários.", description = "Endpoint para listar todos os usuários.")
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/listarUsuarioPorId/{usuarioId}")
    @Operation(summary = "Listar usuário pelo ID dele.", description = "Endpoint para listar um usuário pelo ID.")
    public ResponseEntity<UsuarioDTOResponse> buscarUsuarioPorId(@PathVariable("usuarioId") Integer usuarioId) {
        UsuarioDTOResponse usuario = usuarioService.buscarUsuarioPorId(usuarioId);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Criar novo usuário.", description = "Endpoint para criar um novo registro de usuário.")
    public ResponseEntity<UsuarioDTOResponse> cadastrarUsuario(@Valid @RequestBody UsuarioDTORequest usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(usuarioDTO));
    }

    @PutMapping("/atualizar/{usuarioId}")
    @Operation(summary = "Atualizar todos os dados do usuário.", description = "Endpoint para atualizar o registro do usuário.")
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuarioPorId(
            @PathVariable("usuarioId") Integer usuarioId, @RequestBody UsuarioDTORequest usuarioDTO) {
        UsuarioDTOResponse usuarioAtualizado = usuarioService.atualizarUsuarioPorId(usuarioId, usuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/deletar/{usuarioId}")
    @Operation(summary = "Deletar todos os dados do usuário.", description = "Endpoint para deletar o registro do usuário.")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("usuarioId") Integer usuarioId) {
        usuarioService.deletarUsuarioPorId(usuarioId);
        return ResponseEntity.noContent().build();
    }

}
