package com.vitta.vittaBackend.repository;

import com.vitta.vittaBackend.entity.Medicamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Medicamento m SET m.status = -1 WHERE m.id = :id")
    void apagadoLogicoMedicamento(@Param("id") Integer medicamentoId);

    @Query("SELECT m FROM Medicamento m WHERE m.status > 0")
    List<Medicamento> listarMedicamentos();

    @Query("SELECT m FROM Medicamento m WHERE m.id = :id AND m.status > 0")
    Medicamento obterMedicamentoPeloId(@Param("id") Integer medicamentoId);

    @Query("SELECT m FROM Medicamento m WHERE m.status = 0")
    List<Medicamento> listarMedicamentosInativos();
}
