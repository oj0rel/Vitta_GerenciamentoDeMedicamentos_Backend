package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.dto.request.UsuarioDTORequest;
import com.vitta.vittaBackend.dto.response.UsuarioDTOResponse;
import com.vitta.vittaBackend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) { this.usuarioService = usuarioService; }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioDTOResponse> cadastrarUsuario(@RequestBody UsuarioDTORequest usuarioDTO) {
        UsuarioDTOResponse usuarioResponse = usuarioService.cadastrarUsuario(usuarioDTO);
        return ResponseEntity.ok(usuarioResponse);
    }

    @GetMapping("listarPorId")
    public ResponseEntity<UsuarioDTOResponse> buscarUsuarioPorId(@RequestParam Integer id) {
        UsuarioDTOResponse usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("listar")
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios() {
        List<UsuarioDTOResponse> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("deletar")
    public ResponseEntity<Void> deletarUsuarioPorId(@RequestParam Integer id) {
        usuarioService.deletarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("atualizar")
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuarioPorId(@RequestParam Integer id, @RequestBody UsuarioDTORequest usuarioDTO) {
        UsuarioDTOResponse usuarioAtualizado = usuarioService.atualizarUsuarioPorId(id, usuarioDTO);
        return ResponseEntity.ok(usuarioAtualizado);
    }
}
