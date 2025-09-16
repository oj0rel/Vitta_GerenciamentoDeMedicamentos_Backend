package com.vitta.vittaBackend.repository;

import com.vitta.vittaBackend.entity.Agendamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Agendamento a SET a.status = -1 WHERE a.id = :id")
    void apagadoLogicoAgendamento(@Param("id") Integer agendamentoId);

    @Query("SELECT a FROM Agendamento a WHERE a.status >= 0")
    List<Agendamento> listarAgendamentos();

    @Query("SELECT a FROM Agendamento a WHERE a.id = :id AND a.status >= 0")
    Agendamento obterAgendamentoPeloId(@Param("id") Integer agendamentoId);

    //TESTE PARA LISTAR Agendamentos CANCELADOS
    @Query("SELECT a FROM Agendamento a WHERE a.status = -1")
    List<Agendamento> listarAgendamentosCancelados();
}
