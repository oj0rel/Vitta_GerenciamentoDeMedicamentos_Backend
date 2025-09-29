package com.vitta.vittaBackend.repository;

import com.vitta.vittaBackend.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade {@link Usuario}.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Busca todos os usuários com status ATIVO.
     * Utiliza LEFT JOIN FETCH para carregar os tratamentos associados de forma eficiente.
     * @return Uma lista de usuários ativos.
     */
    @Query("SELECT u FROM Usuario u WHERE u.status > 0")
    List<Usuario> listarUsuariosAtivos();

    /**
     * Busca um usuário ativo pelo seu ID.
     * Utiliza LEFT JOIN FETCH para carregar os tratamentos associados de forma eficiente.
     * @param usuarioId O ID do usuário a ser buscado.
     * @return Um {@link Optional} contendo o usuário, se encontrado e ativo.
     */
    @Query("SELECT u FROM Usuario u WHERE u.id = :id AND u.status >= 0")
    Usuario obterUsuarioPeloId(@Param("id") Integer usuarioId);

    /**
     * Retorna uma lista de todos os usuários que foram logicamente excluídos.
     * Busca por usuários cujo status é igual a -1 (cancelado/inativo).
     * @return Uma lista de entidades {@link Usuario} com o status cancelado.
     */
    @Query("SELECT u FROM Usuario u WHERE u.status = -1")
    List<Usuario> listarUsuariosCancelados();

    /**
     * Busca um usuário pelo seu endereço de e-mail.
     * Essencial para verificar se um e-mail já está em uso durante o cadastro.
     * @param email O e-mail a ser verificado.
     * @return Um {@link Optional} contendo o usuário, se encontrado.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Realiza a exclusão lógica de um usuário diretamente no banco de dados.
     * Esta consulta de atualização (UPDATE) altera o status do usuário para -1 (cancelado/inativo)
     * sem a necessidade de carregar a entidade na memória.
     * A anotação @Modifying é necessária para indicar que esta é uma consulta de alteração.
     * @param usuarioId O ID do usuário a ser desativado.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.status = -1 WHERE u.id = :id")
    void apagadoLogicoUsuario(@Param("id") Integer usuarioId);
}
