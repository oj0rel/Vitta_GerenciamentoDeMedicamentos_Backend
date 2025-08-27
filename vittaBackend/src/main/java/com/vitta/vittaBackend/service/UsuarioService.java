package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.UsuarioDTORequest;
import com.vitta.vittaBackend.dto.response.UsuarioDTOResponse;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;
    private ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    //metodos para Usuario
    //listar usuarios
    public List<UsuarioDTOResponse> listarUsuarios() {
        return this.usuarioRepository.findAll().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOResponse.class))
                .toList();
    }

    //cadastrar usuario
    public UsuarioDTOResponse cadastrarUsuario(UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = modelMapper.map(usuarioDTORequest, Usuario.class);
        Usuario usuarioSalvo = usuarioRepository.saveAndFlush(usuario);
        return modelMapper.map(usuarioSalvo, UsuarioDTOResponse.class);
    }

    //listar 1 usuario, pegando pelo ID
    public UsuarioDTOResponse buscarUsuarioPorId(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("ID do Usuário não encontrado."));
        return modelMapper.map(usuario, UsuarioDTOResponse.class);
    }

    //deletar 1 usuario, pegando pelo ID
    public void deletarUsuarioPorId(Integer id) {
        Usuario usuarioEntity = buscarUsuarioEntityPorId(id); // garante que o Usuario existe, caso nao, ja estoura a exceçao do metodo
        usuarioRepository.deleteById(id);
    }

    //atualizar 1 usuario, pegando pelo ID
    public UsuarioDTOResponse atualizarUsuarioPorId(Integer id, UsuarioDTORequest usuarioDTORequest) {
        Usuario usuarioEntity = buscarUsuarioEntityPorId(id);

        modelMapper.getConfiguration().setSkipNullEnabled(true); //configurando para ignorar nulos
        modelMapper.map(usuarioDTORequest, usuarioEntity); //mapear os campos enviados para o usuario do banco

        Usuario usuarioAtualizado = usuarioRepository.saveAndFlush(usuarioEntity);
        return modelMapper.map(usuarioAtualizado, UsuarioDTOResponse.class);
    }

    //exceção caso a busca pelo Usuario não encontre ninguem
    private Usuario buscarUsuarioEntityPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID de Usuário não encontrado."));
    }
}
