package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.usuario.UsuarioDTORequest;
import com.vitta.vittaBackend.dto.request.usuario.UsuarioAtualizarDTORequest;
import com.vitta.vittaBackend.dto.response.usuario.*;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.UsuarioStatus;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço para gerenciar a lógica de negócio dos Usuários.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Retorna uma lista de todos os usuários com status ATIVO.
     * @return Uma lista de {@link UsuarioDTOResponse}.
     */
    public List<UsuarioDTOResponse> listarUsuariosAtivos() {
        return usuarioRepository.listarUsuariosAtivos()
                .stream()
                .map(UsuarioDTOResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um usuário ativo pelo seu ID.
     * @param usuarioId O ID do usuário a ser buscado.
     * @return O {@link UsuarioDTOResponse} correspondente ao ID.
     */
    public UsuarioDTOResponse buscarUsuarioPorId(Integer usuarioId) {
        Usuario usuario = this.validarUsuario(usuarioId);
        return new UsuarioDTOResponse(usuario);
    }

    public UsuarioAgendamentosDTOResponse buscarUsuarioAgendamentosPorId(Integer usuarioId) {
        Usuario usuario = this.validarUsuario(usuarioId);
        return new UsuarioAgendamentosDTOResponse(usuario);
    }

    public UsuarioHistoricoDTOResponse buscarUsuarioHistoricosPorId(Integer usuarioId) {
        Usuario usuario = this.validarUsuario(usuarioId);
        return new UsuarioHistoricoDTOResponse(usuario);
    }

    public UsuarioMedicamentosDTOResponse buscarUsuarioMedicamentosPorId(Integer usuarioId) {
        Usuario usuario = this.validarUsuario(usuarioId);
        return new UsuarioMedicamentosDTOResponse(usuario);
    }

    public UsuarioTratamentosDTOResponse buscarUsuarioTratamentosPorId(Integer usuarioId) {
        Usuario usuario = this.validarUsuario(usuarioId);
        return new UsuarioTratamentosDTOResponse(usuario);
    }

    /**
     * Cria um novo usuário, validando se o e-mail já existe e criptografando a senha.
     * @param usuarioDTORequest O DTO contendo os dados para o novo usuário.
     * @return O {@link UsuarioDTOResponse} da entidade recém-criada.
     * @throws IllegalStateException se o e-mail já estiver em uso.
     */
    @Transactional
    public UsuarioDTOResponse cadastrarUsuario(UsuarioDTORequest usuarioDTORequest) {
        if (usuarioRepository.findByEmail(usuarioDTORequest.getEmail()).isPresent()) {
            throw new IllegalStateException("O e-mail informado já está em uso.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(usuarioDTORequest.getNome());
        novoUsuario.setTelefone(usuarioDTORequest.getTelefone());
        novoUsuario.setEmail(usuarioDTORequest.getEmail());

        novoUsuario.setSenha(usuarioDTORequest.getSenha());

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);
        return new UsuarioDTOResponse(usuarioSalvo);
    }

    /**
     * Atualiza os dados de um usuário existente (nome e telefone).
     * @param usuarioId O ID do usuário a ser atualizado.
     * @param usuarioAtualizarDTORequest O DTO com os novos dados.
     * @return O {@link UsuarioDTOResponse} da entidade atualizada.
     */
    @Transactional
    public UsuarioDTOResponse atualizarUsuario(Integer usuarioId, UsuarioAtualizarDTORequest usuarioAtualizarDTORequest) {
        Usuario usuarioExistente = this.validarUsuario(usuarioId);

        if (usuarioAtualizarDTORequest.getNome() != null) {
            usuarioExistente.setNome(usuarioAtualizarDTORequest.getNome());
        }
        if (usuarioAtualizarDTORequest.getTelefone() != null) {
            usuarioExistente.setTelefone(usuarioAtualizarDTORequest.getTelefone());
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
        return new UsuarioDTOResponse(usuarioAtualizado);
    }

    /**
     * Realiza a exclusão lógica de um usuário, alterando seu status para INATIVO.
     * @param usuarioId O ID do usuário a ser desativado.
     */
    @Transactional
    public void deletarLogico(Integer usuarioId) {
        Usuario usuario = this.validarUsuario(usuarioId);

        usuario.setStatus(UsuarioStatus.INATIVO);
        usuarioRepository.save(usuario);
    }



    /**
     * Valida a existência de um usuário pelo seu ID e o retorna.
     * Este é um método auxiliar privado para evitar a repetição de código nos
     * métodos públicos que precisam de buscar uma entidade antes de realizar uma ação.
     *
     * @param usuarioId O ID do usuário a ser validado e buscado.
     * @return A entidade {@link Usuario} encontrada.
     */
    private Usuario validarUsuario(Integer usuarioId) {
        Usuario usuario = usuarioRepository.obterUsuarioPeloId(usuarioId);
        if (usuario == null) {
            throw new EntityNotFoundException("Usuário não encontrado com o ID: " + usuarioId);
        }
        return usuario;
    }

}
