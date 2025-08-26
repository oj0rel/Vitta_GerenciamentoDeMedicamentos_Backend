package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.UsuarioDTORequest;
import com.vitta.vittaBackend.dto.response.UsuarioDTOResponse;
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

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = new ModelMapper();
    }

    //metodos para Usuario
    //listar usuarios
    public List<Usuario> listarUsuarios() {
        return this.usuarioRepository.findAll();
    }

    //cadastrar usuario
    public UsuarioDTOResponse cadastrarUsuario(UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = modelMapper.map(usuarioDTORequest, Usuario.class);
        Usuario usuarioSalvo = usuarioRepository.saveAndFlush(usuario);
        return modelMapper.map(usuarioSalvo, UsuarioDTOResponse.class);
    }

    //listar 1 usuario, pegando pelo ID
    public Usuario buscarUsuarioPorId(Integer usuarioId) {
        return this.usuarioRepository.findById(usuarioId).orElse(null);
    }

    //deletar 1 usuario, pegando pelo ID
    public void deletarUsuarioPorId(Integer id) {
        Usuario usuarioEntity = buscarUsuarioPorId(id); // garante que o Usuario existe, caso nao, ja estoura a exceçao do metodo
        usuarioRepository.deleteById(id);
    }

    //atualizar 1 usuario, pegando pelo ID
    public Usuario atualizarUsuarioPorId(Integer id, Usuario usuario) {
        Usuario usuarioEntity = buscarUsuarioPorId(id);

        modelMapper.getConfiguration().setSkipNullEnabled(true); //configurando para ignorar nulos
        modelMapper.map(usuario, usuarioEntity); //mapear os campos enviados para o usuario do banco

        return usuarioRepository.saveAndFlush(usuarioEntity);
    }
}
