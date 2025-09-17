package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.dto.request.usuario.UsuarioDTORequest;
import com.vitta.vittaBackend.dto.request.usuario.UsuarioDTORequestAtualizar;
import com.vitta.vittaBackend.dto.response.agenda.AgendaDoDiaDTOResponse;
import com.vitta.vittaBackend.dto.response.usuario.UsuarioDTOResponse;
import com.vitta.vittaBackend.service.AgendaService;
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
    private AgendaService agendaService;

    public UsuarioController(UsuarioService usuarioService, AgendaService agendaService) {
        this.usuarioService = usuarioService;
        this.agendaService = agendaService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar Usuários.", description = "Endpoint para listar todos os Usuários.")
    public ResponseEntity <List<UsuarioDTOResponse>> listarUsuarios() { return ResponseEntity.ok(usuarioService.listarUsuarios()); }

    @GetMapping("/listarUsuarioPorId/{usuarioId}")
    @Operation(summary = "Listar Usuário pelo ID dele.", description = "Endpoint para listar um Usuário, pelo ID.")
    public ResponseEntity<UsuarioDTOResponse> buscarUsuarioPorId(@PathVariable("usuarioId") Integer usuarioId) {
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.buscarUsuarioPorId(usuarioId);

        if (usuarioId == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usuarioDTOResponse);
        }
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Criar novo Usuário.", description = "Endpoint para criar um novo registro de Usuário.")
    public ResponseEntity<UsuarioDTOResponse> cadastrarUsuario(@Valid @RequestBody UsuarioDTORequest usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(usuarioDTO));
    }

    @PutMapping("/atualizar/{usuarioId}")
    @Operation(summary = "Atualizar todos os dados do Usuário.", description = "Endpoint para atualizar o registro do Usuário, pelo ID.")
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuarioPorId(
            @PathVariable("usuarioId") Integer usuarioId,
            @RequestBody @Valid UsuarioDTORequestAtualizar usuarioDTO) {
        UsuarioDTOResponse usuarioAtualizado = usuarioService.atualizarUsuarioPorId(usuarioId, usuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/deletar/{usuarioId}")
    @Operation(summary = "Deletar todos os dados do Usuário.", description = "Endpoint para deletar o registro do Usuário, pelo ID.")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("usuarioId") Integer usuarioId) {
        usuarioService.deletarUsuario(usuarioId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/listar/cancelados")
    @Operation(summary = "Listar Usuários cancelados.", description = "Endpoint para listar todos os Usuários cancelados.")
    public ResponseEntity <List<UsuarioDTOResponse>> listarUsuariosCancelados() { return ResponseEntity.ok(usuarioService.listarUsuariosCancelados()); }

    @GetMapping("/{usuarioId}/agenda-do-dia")
    public ResponseEntity<List<AgendaDoDiaDTOResponse>> getAgendaDoDia(@PathVariable int id) {
        List<AgendaDoDiaDTOResponse> agenda = agendaService.getAgendaDoDia(id);
        if (agenda.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se a agenda estiver vazia
        }
        return ResponseEntity.ok(agenda); // Retorna 200 OK com a lista
    }
}
