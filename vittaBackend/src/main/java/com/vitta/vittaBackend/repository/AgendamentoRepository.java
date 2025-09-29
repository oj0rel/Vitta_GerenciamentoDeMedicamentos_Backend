package com.vitta.vittaBackend.repository;

import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.enums.agendamento.AgendamentoStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    /**
     * Retorna uma lista de todos os agendamentos considerados ativos.
     * A consulta busca por agendamentos cujo status é maior que 0.
     * @return Uma lista de entidades {@link Agendamento} ativas.
     */
    @Query("SELECT a FROM Agendamento a WHERE a.status > 0")
    List<Agendamento> listarAgendamentos();

    /**
     * Busca um agendamento específico pelo seu ID.
     * @param agendamentoId O ID do agendamento a ser buscado.
     * @return A entidade {@link Agendamento} correspondente, ou {@code null} se não for encontrada.
     */
    @Query("SELECT a FROM Agendamento a WHERE a.id = :id AND a.status >= 0")
    Agendamento obterAgendamentoPeloId(@Param("id") Integer agendamentoId);

    /**
     * Realiza a exclusão lógica de um agendamento diretamente no banco de dados.
     * Esta consulta de atualização (UPDATE) altera o status do agendamento para 0 (inativo).
     * @param agendamentoId O ID do agendamento a ser desativado.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Agendamento a SET a.status = 0 WHERE a.id = :id")
    void apagadoLogicoAgendamento(@Param("id") Integer agendamentoId);

    /**
     * Encontra o primeiro agendamento associado a um ID de tratamento específico.
     * "findFirst" - Limita o resultado ao primeiro encontrado (equivalente a "findTop").
     * "ByTratamentoId" - Filtra pela propriedade 'id' da entidade relacionada 'tratamento'.
     *
     * @param tratamentoId o ID do tratamento a ser buscado.
     * @return um Optional contendo o primeiro agendamento encontrado, ou um Optional vazio se nenhum for encontrado.
     */
    Optional<Agendamento> findFirstByTratamentoId(Integer tratamentoId);

    /**
     * Deleta todos os agendamentos de um tratamento que estão com status PENDENTE
     * e cuja data/hora é futura em relação ao momento atual.
     *
     * @param tratamentoId O ID do tratamento.
     * @param agora O momento atual, para garantir que apenas agendamentos futuros sejam apagados.
     */
    @Modifying
    void deleteByTratamentoIdAndStatusAndHorarioDoAgendamentoAfter(Integer tratamentoId, AgendamentoStatus status, LocalDateTime agora);
}
