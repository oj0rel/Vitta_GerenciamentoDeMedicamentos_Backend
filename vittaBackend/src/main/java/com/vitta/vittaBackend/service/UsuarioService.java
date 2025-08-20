package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios() { return this.usuarioRepository.findAll(); }
}
