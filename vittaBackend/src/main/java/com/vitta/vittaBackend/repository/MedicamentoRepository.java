package com.vitta.vittaBackend.repository;

import com.vitta.vittaBackend.entity.Medicamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade {@link Medicamento}.
 * Estende JpaRepository para funcionalidades CRUD padrão e define consultas customizadas.
 */
@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {

    /**
     * Retorna uma lista de todos os medicamentos considerados ativos.
     * A consulta busca por medicamentos cujo status é maior que 0.
     * @return Uma lista de entidades {@link Medicamento} ativas.
     */
    @Query("SELECT m FROM Medicamento m WHERE m.status > 0")
    List<Medicamento> listarMedicamentos();

    /**
     * Busca um medicamento específico pelo seu ID, contanto que não esteja inativo (status >= 0).
     * @param medicamentoId O ID do medicamento a ser buscado.
     * @return A entidade {@link Medicamento} correspondente, ou {@code null} se não for encontrada ou estiver com um status negativo.
     */
    @Query("SELECT m FROM Medicamento m WHERE m.id = :id AND m.status >= 0")
    Medicamento obterMedicamentoPeloId(@Param("id") Integer medicamentoId);

    /**
     * Retorna uma lista de todos os medicamentos considerados inativos (excluídos logicamente).
     * A consulta busca por medicamentos cujo status é igual a 0.
     * @return Uma lista de entidades {@link Medicamento} inativas.
     */
    @Query("SELECT m FROM Medicamento m WHERE m.status = 0")
    List<Medicamento> listarMedicamentosInativos();

    /**
     * Realiza a exclusão lógica de um medicamento diretamente no banco de dados.
     * Esta consulta de atualização (UPDATE) altera o status do medicamento para 0 (inativo).
     * A anotação @Modifying é necessária para indicar que esta é uma consulta de alteração de dados.
     * @param medicamentoId O ID do medicamento a ser desativado.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Medicamento m SET m.status = 0 WHERE m.id = :id")
    void apagadoLogicoMedicamento(@Param("id") Integer medicamentoId);
}
