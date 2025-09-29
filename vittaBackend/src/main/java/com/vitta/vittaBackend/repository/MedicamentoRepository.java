package com.vitta.vittaBackend.repository;

import com.vitta.vittaBackend.entity.Medicamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade {@link Medicamento}.
 * Estende JpaRepository para funcionalidades CRUD padrão e define consultas customizadas.
 */
@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {

    /**
     * Retorna uma lista de todos os medicamentos ativos de um usuário específico.
     * A consulta busca por medicamentos cujo status é maior que 0 e que pertencem ao usuário informado.
     *
     * @param usuarioId O ID do usuário para o qual os medicamentos serão listados.
     * @return Uma lista de entidades {@link Medicamento} ativas para o usuário especificado.
     */
    @Query("SELECT m FROM Medicamento m WHERE m.usuario.id = :usuarioId AND m.status > 0")
    List<Medicamento> listarMedicamentos(@Param("usuarioId") Integer usuarioId);

    /**
     * Busca um medicamento específico pelo seu ID e pelo ID do usuário proprietário.
     * Garante que um usuário só possa acessar seus próprios medicamentos.
     *
     * @param medicamentoId O ID do medicamento a ser buscado.
     * @param usuarioId O ID do usuário proprietário do medicamento.
     * @return um {@link Optional} contendo a entidade {@link Medicamento} correspondente, ou vazio se não for encontrada ou não pertencer ao usuário.
     */
    @Query("SELECT m FROM Medicamento m WHERE m.id = :medicamentoId AND m.usuario.id = :usuarioId AND m.status >= 0")
    Optional<Medicamento> listarMedicamentoPorId(@Param("medicamentoId") Integer medicamentoId, @Param("usuarioId") Integer usuarioId);

    /**
     * Retorna uma lista de todos os medicamentos inativos (excluídos logicamente) de um usuário específico.
     * A consulta busca por medicamentos cujo status é igual a 0.
     *
     * @param usuarioId O ID do usuário para o qual os medicamentos inativos serão listados.
     * @return Uma lista de entidades {@link Medicamento} inativas para o usuário especificado.
     */
    @Query("SELECT m FROM Medicamento m WHERE m.usuario.id = :usuarioId AND m.status = 0")
    List<Medicamento> listarMedicamentosInativos(@Param("usuarioId") Integer usuarioId);

    /**
     * Realiza a exclusão lógica de um medicamento, garantindo que ele pertença ao usuário correto.
     * Esta consulta de atualização (UPDATE) altera o status do medicamento para 0 (inativo).
     *
     * @param medicamentoId O ID do medicamento a ser desativado.
     * @param usuarioId O ID do usuário proprietário do medicamento.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Medicamento m SET m.status = 0 WHERE m.id = :medicamentoId AND m.usuario.id = :usuarioId")
    void apagarLogicoMedicamento(@Param("medicamentoId") Integer medicamentoId, @Param("usuarioId") Integer usuarioId);
}
