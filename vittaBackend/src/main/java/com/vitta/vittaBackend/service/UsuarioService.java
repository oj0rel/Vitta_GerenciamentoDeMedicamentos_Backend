package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.UsuarioDTORequest;
import com.vitta.vittaBackend.dto.response.UsuarioDTOResponse;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.OrderStatus;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //LISTAR USUÁRIOS
    public List<UsuarioDTOResponse> listarUsuarios() {
        return this.usuarioRepository.listarUsuarios()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOResponse.class))
                .collect(Collectors.toList());
    }

    //LISTAR 1 USUÁRIO, PEGANDO PELO ID
    public UsuarioDTOResponse listarUsuarioPorId(Integer usuarioId) {
        Usuario usuario = usuarioRepository.obterUsuarioPeloId(usuarioId);

        return modelMapper.map(usuario, UsuarioDTOResponse.class);
    }


    //CADASTRAR USUÁRIO
    @Transactional
    public UsuarioDTOResponse cadastrarUsuario(UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = modelMapper.map(usuarioDTORequest, Usuario.class);
        usuario.setStatus(OrderStatus.ATIVO.getCode());
        Usuario usuarioSave = this.usuarioRepository.save(usuario);
        UsuarioDTOResponse usuarioDTOResponse = modelMapper.map(usuarioSave, UsuarioDTOResponse.class);
        return usuarioDTOResponse;
    }

    //ATUALIZAR 1 USUÁRIO, PEGANDO PELO ID
    @Transactional
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

    //DELETAR 1 USUÁRIO, PEGANDO PELO ID
    @Transactional
    public void deletarUsuario(Integer usuarioId) { usuarioRepository.apagadoLogicoUsuario(usuarioId); }

    //METODO PRIVADO PARA VALIDAR SE A IDENTIDADE EXISTE, PEGANDO PELO ID - para utilizar em outros métodos
    private Usuario validarUsuario(Integer usuarioId) {
        Usuario usuario = usuarioRepository.obterUsuarioPeloId(usuarioId);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado ou inativo.");
        }
        return usuario;
    }

}
