package com.vitta.vittaBackend.repository;

import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.enums.agendamento.AgendamentoStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

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
     * Busca todos os agendamentos para um usuário específico dentro de um
     * intervalo de data e hora.
     * @param usuarioId O ID do usuário.
     * @param inicioDoDia A data e hora de início do período (ex: hoje às 00:00:00).
     * @param fimDoDia A data e hora de fim do período (ex: hoje às 23:59:59).
     * @return Uma lista de agendamentos.
     */
//    @Query("SELECT a FROM Agendamento a JOIN FETCH a.medicamento JOIN FETCH a.usuario WHERE a.usuario.id = :usuarioId AND a.horarioDoAgendamento BETWEEN :inicioDoDia AND :fimDoDia ORDER BY a.horarioDoAgendamento ASC")
//    List<Agendamento> findByUsuarioIdAndData(
//            @Param("usuarioId") int usuarioId,
//            @Param("inicioDoDia") LocalDateTime inicioDoDia,
//            @Param("fimDoDia") LocalDateTime fimDoDia
//    );

    /**
     * Deleta todos os agendamentos de um tratamento que estão com status PENDENTE
     * e cuja data/hora é futura em relação ao momento atual.
     * @param tratamentoId O ID do tratamento.
     * @param agora O momento atual, para garantir que apenas agendamentos futuros sejam apagados.
     */
    @Modifying
    void deleteByTratamentoIdAndStatusAndHorarioDoAgendamentoAfter(Integer tratamentoId, AgendamentoStatus status, LocalDateTime agora);
}
