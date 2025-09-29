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
     * Retorna uma lista de todos os agendamentos ativos de um usuário específico.
     * A consulta busca por agendamentos cujo status é maior que 0.
     *
     * @param usuarioId O ID do usuário dono dos agendamentos.
     * @return Uma lista de entidades {@link Agendamento} ativas para o usuário especificado.
     */
    @Query("SELECT a FROM Agendamento a WHERE a.usuario.id = :usuarioId AND a.status > 0")
    List<Agendamento> listarAgendamentosAtivos(@Param("usuarioId") Integer usuarioId);

    /**
     * Busca um agendamento específico pelo seu ID e pelo ID do usuário proprietário.
     * Garante que um usuário só possa acessar seus próprios agendamentos.
     *
     * @param agendamentoId O ID do agendamento a ser buscado.
     * @param usuarioId O ID do usuário que deve ser o proprietário do agendamento.
     * @return um {@link Optional} contendo a entidade {@link Agendamento} correspondente, ou vazio se não for encontrada ou não pertencer ao usuário.
     */
    @Query("SELECT a FROM Agendamento a WHERE a.id = :agendamentoId AND a.usuario.id = :usuarioId AND a.status >= 0")
    Optional<Agendamento> listarAgendamentoPorId(@Param("agendamentoId") Integer agendamentoId, @Param("usuarioId") Integer usuarioId);

    /**
     * Realiza a exclusão lógica de um agendamento, garantindo que pertença ao usuário correto.
     * Esta consulta de atualização (UPDATE) altera o status do agendamento para 0 (inativo).
     *
     * @param agendamentoId O ID do agendamento a ser desativado.
     * @param usuarioId O ID do usuário proprietário do agendamento.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Agendamento a SET a.status = 0 WHERE a.id = :agendamentoId AND a.usuario.id = :usuarioId")
    void apagarLogicoAgendamento(@Param("agendamentoId") Integer agendamentoId, @Param("usuarioId") Integer usuarioId);

    /**
     * Encontra o primeiro agendamento associado a um ID de tratamento específico.
     * "findFirst" - Limita o resultado ao primeiro encontrado (equivalente a "findTop").
     * "ByTratamentoId" - Filtra pela propriedade 'id' da entidade relacionada 'tratamento'.
     *
     * @param tratamentoId o ID do tratamento a ser buscado.
     * @param usuarioId o ID do usuário proprietário.
     * @return um Optional contendo o primeiro agendamento encontrado, ou um Optional vazio se nenhum for encontrado.
     */
    @Query("SELECT a FROM Agendamento a\n" +
            "WHERE a.tratamento.id = :tratamentoId\n" +
            "  AND a.usuario.id = :usuarioId\n" +
            "ORDER BY a.id ASC\n" +
            "LIMIT 1")
    Optional<Agendamento> obterPrimeiroAgendamentoDeTratamentoEspecifico(Integer tratamentoId, Integer usuarioId);

    /**
     * Deleta todos os agendamentos de um tratamento que estão com status PENDENTE
     * e cuja data/hora é futura em relação ao momento atual.
     *
     * @param tratamentoId O ID do tratamento.
     * @param usuarioId O ID do usuário proprietário dos agendamentos.
     * @param status O status do agendamento.
     * @param agora O momento atual, para garantir que apenas agendamentos futuros sejam apagados.
     */
    @Modifying
    @Query("DELETE FROM Agendamento a\n" +
            "WHERE a.tratamento.id = :tratamentoId\n" +
            "  AND a.usuario.id = :usuarioId\n" +
            "  AND a.status = :status\n" +
            "  AND a.horarioDoAgendamento > :agora")
    void deletarAgendamentosFuturosPendentesDeTratamento(
            @Param("tratamentoId") Integer tratamentoId,
            @Param("usuarioId") Integer usuarioId,
            @Param("status") AgendamentoStatus status,
            @Param("agora") LocalDateTime agora
    );
}
