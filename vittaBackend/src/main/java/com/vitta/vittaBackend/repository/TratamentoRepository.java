package com.vitta.vittaBackend.repository;

import com.vitta.vittaBackend.entity.Tratamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade {@link Tratamento}.
 */
@Repository
public interface TratamentoRepository extends JpaRepository<Tratamento, Integer> {

    /**
     * Retorna uma lista de todos os tratamentos ativos de um usuário específico.
     * A consulta busca por tratamentos cujo status é maior que 0.
     *
     * @param usuarioId O ID do usuário para o qual os tratamentos serão listados.
     * @return Uma lista de entidades {@link Tratamento} ativas para o usuário especificado.
     */
    @Query("SELECT t FROM Tratamento t WHERE t.usuario.id = :usuarioId AND t.status > 0")
    List<Tratamento> listarTratamentos(@Param("usuarioId") Integer usuarioId);

    /**
     * Busca um tratamento específico pelo seu ID e pelo ID do usuário proprietário.
     * Garante que um usuário só possa acessar seus próprios tratamentos.
     *
     * @param tratamentoId O ID do tratamento a ser buscado.
     * @param usuarioId O ID do usuário proprietário do tratamento.
     */
    @Query("SELECT t FROM Tratamento t WHERE t.id = :tratamentoId AND t.usuario.id = :usuarioId")
    Tratamento listarTratamentoPorId(@Param("tratamentoId") Integer tratamentoId, @Param("usuarioId") Integer usuarioId);

    /**
     * Realiza a exclusão lógica de um tratamento, garantindo que ele pertença ao usuário correto.
     * Esta consulta de atualização (UPDATE) altera o status do tratamento para 0 (inativo/cancelado).
     *
     * @param tratamentoId O ID do tratamento a ser desativado.
     * @param usuarioId O ID do usuário proprietário do tratamento.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Tratamento t SET t.status = 0 WHERE t.id = :tratamentoId AND t.usuario.id = :usuarioId")
    void apagarLogicoTratamento(@Param("tratamentoId") Integer tratamentoId, @Param("usuarioId") Integer usuarioId);

    /**
     * Verifica de forma eficiente se um tratamento com um determinado ID
     * e pertencente a um usuário específico existe no banco de dados.
     *
     * @param tratamentoId o ID do tratamento.
     * @param usuarioId o ID do usuário.
     * @return true se o tratamento existir, false caso contrário.
     */
    boolean existsByIdAndUsuarioId(Integer tratamentoId, Integer usuarioId);
}
