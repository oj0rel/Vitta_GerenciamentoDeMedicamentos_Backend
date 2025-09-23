package com.vitta.vittaBackend.repository;

import com.vitta.vittaBackend.entity.Tratamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para gerenciar as operações de banco de dados para a entidade {@link Tratamento}.
 */
@Repository
public interface TratamentoRepository extends JpaRepository<Tratamento, Integer> {

    /**
     * Busca todos os tratamentos com status ATIVO.
     * @return Uma lista de tratamentos ativos.
     */
    @Query("SELECT t FROM Tratamento t WHERE t.status > 0")
    List<Tratamento> listarTratamentos();

    /**
     * Busca um tratamento ativo pelo seu ID.
     * @param tratamentoId O ID do tratamento a ser buscado.
     */
    @Query("SELECT t FROM Tratamento t WHERE t.id = :id AND t.status >= 0")
    Tratamento obterTratamentoPeloId(@Param("tratamentoId") Integer tratamentoId);

    /**
     * Realiza a exclusão lógica de um tratamento diretamente no banco de dados.
     * Esta consulta de atualização (UPDATE) altera o status do tratamento para 0 (cancelado)
     * sem a necessidade de carregar a entidade na memória.
     * @param tratamentoId O ID do tratamento a ser desativado.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Tratamento t SET t.status = 0 WHERE t.id = :id")
    void apagadoLogicoTratamento(@Param("tratamentoId") Integer tratamentoId);
}
