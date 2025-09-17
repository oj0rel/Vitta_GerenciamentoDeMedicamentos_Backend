package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.usuario.UsuarioDTORequest;
import com.vitta.vittaBackend.dto.request.usuario.UsuarioDTORequestAtualizar;
import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoResumoDTOResponse;
import com.vitta.vittaBackend.dto.response.usuario.UsuarioDTOResponse;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.repository.MedicamentoRepository;
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
    private final MedicamentoRepository medicamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, MedicamentoRepository medicamentoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    //LISTAR USUÁRIOS
    public List<UsuarioDTOResponse> listarUsuarios() {
        return this.usuarioRepository.listarUsuarios()
                .stream()
                .map(usuario -> {
                    // ignora medicamentos no mapeamento automático
                    UsuarioDTOResponse usuarioDTOResponse = modelMapper
                            .typeMap(Usuario.class, UsuarioDTOResponse.class)
                            .addMappings(mapper -> mapper.skip(UsuarioDTOResponse::setMedicamentos))
                            .map(usuario);

                    // popula medicamentos manualmente
                    List<MedicamentoResumoDTOResponse> medicamentosDTO = usuario.getMedicamentos().stream()
                            .map(med -> modelMapper.map(med, MedicamentoResumoDTOResponse.class))
                            .collect(Collectors.toList());

                    usuarioDTOResponse.setMedicamentos(medicamentosDTO);
                    return usuarioDTOResponse;
                })
                .collect(Collectors.toList());
    }



    //LISTAR 1 USUÁRIO, PEGANDO PELO ID
    public UsuarioDTOResponse buscarUsuarioPorId(Integer usuarioId) {
        Usuario usuario = usuarioRepository.obterUsuarioPeloId(usuarioId);

        return modelMapper.map(usuario, UsuarioDTOResponse.class);
    }


    //CADASTRAR USUÁRIO
    @Transactional
    public UsuarioDTOResponse cadastrarUsuario(UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = new Usuario();

        usuario.setNome(usuarioDTORequest.getNome());
        usuario.setTelefone(usuarioDTORequest.getTelefone());
        usuario.setEmail(usuarioDTORequest.getEmail());
        usuario.setSenha(usuarioDTORequest.getSenha());

        Usuario usuarioSave = usuarioRepository.save(usuario);
        return modelMapper.map(usuarioSave, UsuarioDTOResponse.class);
    }

    //ATUALIZAR 1 USUÁRIO, PEGANDO PELO ID
    @Transactional
    public UsuarioDTOResponse atualizarUsuarioPorId(Integer usuarioId, UsuarioDTORequestAtualizar usuarioDTORequestAtualizar) {
        Usuario usuario = this.validarUsuario(usuarioId);

        if (usuario != null) {
            usuario.setNome(usuarioDTORequestAtualizar.getNome());
            usuario.setTelefone(usuarioDTORequestAtualizar.getTelefone());

            Usuario usuarioSave = usuarioRepository.save(usuario);
            return modelMapper.map(usuarioSave, UsuarioDTOResponse.class);
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


    //METODO PARA TESTE DE ENDPOINT
    //LISTAR USUÁRIOS CANCELADOS
    public List<UsuarioDTOResponse> listarUsuariosCancelados() {
        return this.usuarioRepository.listarUsuariosCancelados()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOResponse.class))
                .collect(Collectors.toList());
    }

}
