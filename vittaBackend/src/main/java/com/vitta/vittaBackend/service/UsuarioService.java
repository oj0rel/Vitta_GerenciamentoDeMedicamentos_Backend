package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = new ModelMapper();
    }

    //metodos para Usuario
    public List<Usuario> listarUsuarios() {
        return this.usuarioRepository.findAll();
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        return usuarioRepository.saveAndFlush(usuario);
    }

    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID de Usuário não encontrado.")
        );
    }

    public void deletarUsuarioPorId(Integer id) {
        Usuario usuarioEntity = buscarUsuarioPorId(id); // garante que o Usuario existe, caso nao, ja estoura a exceçao do metodo
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizarUsuarioPorId(Integer id, Usuario usuario) {
        Usuario usuarioEntity = buscarUsuarioPorId(id);

        modelMapper.getConfiguration().setSkipNullEnabled(true); //configurando para ignorar nulos
        modelMapper.map(usuario, usuarioEntity); //mapear os campos enviados para o usuario do banco

        return usuarioRepository.saveAndFlush(usuarioEntity);
    }
}
