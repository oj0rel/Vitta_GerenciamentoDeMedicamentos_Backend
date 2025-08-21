package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) { this.usuarioService = usuarioService; }

    @PostMapping("cadastrar")
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("listarPorId")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@RequestParam Integer id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("deletar")
    public ResponseEntity<Void> deletarUsuarioPorId(@RequestParam Integer id) {
        usuarioService.deletarUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("atualizar")
    public ResponseEntity<Usuario> atualizarUsuarioPorId(@RequestParam Integer id, @RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuarioPorId(id, usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }
}
