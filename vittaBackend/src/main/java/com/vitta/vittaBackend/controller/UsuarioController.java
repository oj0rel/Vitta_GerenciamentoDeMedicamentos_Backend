package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
@RestController
@RequestMapping({"api/usuario"})
public class UsuarioController {

    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) { this.usuarioService = usuarioService; }

    @GetMapping({"listar"})
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(this.usuarioService.listarUsuarios());
    }
}
