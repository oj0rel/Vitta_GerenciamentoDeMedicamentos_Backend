package com.vitta.vittaBackend.repository;

import com.vitta.vittaBackend.entity.Agendamento;
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

    @Modifying
    @Transactional
    @Query("UPDATE Agendamento a SET a.status = 0 WHERE a.id = :id")
    void apagadoLogicoAgendamento(@Param("id") Integer agendamentoId);

    @Query("SELECT a FROM Agendamento a " +
            "LEFT JOIN FETCH a.usuario u " +
            "LEFT JOIN FETCH a.medicamento m " +
            "LEFT JOIN FETCH a.medicamentoHistorico mh " +
            "WHERE a.status >= 0")
    List<Agendamento> listarAgendamentos();

    @Query("SELECT a FROM Agendamento a WHERE a.id = :id AND a.status >= 0")
    Agendamento obterAgendamentoPeloId(@Param("id") Integer agendamentoId);

    @Query("SELECT a FROM Agendamento a WHERE a.status = 0")
    List<Agendamento> listarAgendamentosInativos();

    /**
     * Busca todos os agendamentos para um usuário específico dentro de um
     * intervalo de data e hora.
     * @param usuarioId O ID do usuário.
     * @param inicioDoDia A data e hora de início do período (ex: hoje às 00:00:00).
     * @param fimDoDia A data e hora de fim do período (ex: hoje às 23:59:59).
     * @return Uma lista de agendamentos.
     */
    @Query("SELECT a FROM Agendamento a JOIN FETCH a.medicamento JOIN FETCH a.usuario WHERE a.usuario.id = :usuarioId AND a.horarioDoAgendamento BETWEEN :inicioDoDia AND :fimDoDia ORDER BY a.horarioDoAgendamento ASC")
    List<Agendamento> findByUsuarioIdAndData(
            @Param("usuarioId") int usuarioId,
            @Param("inicioDoDia") LocalDateTime inicioDoDia,
            @Param("fimDoDia") LocalDateTime fimDoDia
    );
}
