package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.UsuarioDTORequest;
import com.vitta.vittaBackend.dto.response.UsuarioDTOResponse;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.OrderStatus;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //metodos para Usuario
    //listar usuarios
    public List<UsuarioDTOResponse> listarUsuarios() {
        return this.usuarioRepository.findAll().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOResponse.class))
                .toList();
    }

    //listar 1 usuario, pegando pelo ID
    public UsuarioDTOResponse buscarUsuarioPorId(Integer usuarioId) {
        Usuario usuarioEncontrado = this.validarUsuario(usuarioId);
        UsuarioDTOResponse usuarioDTOResponse = modelMapper.map(usuarioEncontrado, UsuarioDTOResponse.class);
        return usuarioDTOResponse;
    }


    //cadastrar usuario
    public UsuarioDTOResponse cadastrarUsuario(UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = modelMapper.map(usuarioDTORequest, Usuario.class);
        usuario.setStatus(OrderStatus.ATIVO.getCode());
        Usuario usuarioSave = this.usuarioRepository.save(usuario);
        UsuarioDTOResponse usuarioDTOResponse = modelMapper.map(usuarioSave, UsuarioDTOResponse.class);
        return usuarioDTOResponse;
    }

    //atualizar 1 usuario, pegando pelo ID
    public UsuarioDTOResponse atualizarUsuarioPorId(Integer usuarioId, UsuarioDTORequest usuarioDTORequest) {
        Usuario usuarioBuscado = this.validarUsuario(usuarioId);

        if (usuarioBuscado != null) {
            modelMapper.map(usuarioDTORequest, usuarioBuscado);
            Usuario usuarioRecebido = usuarioRepository.save(usuarioBuscado);
            return modelMapper.map(usuarioRecebido, UsuarioDTOResponse.class);
        } else {
            return null;
        }
    }

    //deletar 1 usuario, pegando pelo ID
    public void deletarUsuarioPorId(Integer usuarioId) {
        Usuario usuarioEntity = this.validarUsuario(usuarioId); // garante que o Usuario existe, caso nao, ja estoura a exceçao do metodo
        usuarioRepository.deleteById(usuarioId);
    }

    // metodo privado para validar se a entidade existe pelo ID
    private Usuario validarUsuario(Integer usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("ID do Usuário não encontrado."));
    }

}
