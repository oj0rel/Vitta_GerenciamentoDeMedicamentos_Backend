package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

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

    public void cadastrarUsuario(Usuario usuario) {
        usuarioRepository.saveAndFlush(usuario);
    }

    public Usuario buscarUsuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow( //usado para criar uma exceçao informativa, caso dê erro
                () -> new RuntimeException("ID de Usuário não encontrado.")
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
