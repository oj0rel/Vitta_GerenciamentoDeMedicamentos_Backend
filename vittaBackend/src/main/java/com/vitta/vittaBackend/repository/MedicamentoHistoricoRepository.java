package com.vitta.vittaBackend.repository;

import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.entity.MedicamentoHistorico;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicamentoHistoricoRepository extends JpaRepository<MedicamentoHistorico, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE MedicamentoHistorico m SET m.historicoStatus = 0 WHERE m.id = :id")
    void apagadoLogicoMedicamentoHistorico(@Param("id") Integer medicamentoHistoricoId);

    @Query("SELECT m FROM MedicamentoHistorico m WHERE m.historicoStatus > 0")
    List<MedicamentoHistorico> listarMedicamentosHistoricos();

    @Query("SELECT m FROM MedicamentoHistorico m WHERE m.id = :id AND m.historicoStatus >= 0")
    MedicamentoHistorico obterMedicamentoHistoricoPeloId(@Param("id") Integer medicamentoHistoricoId);
}
