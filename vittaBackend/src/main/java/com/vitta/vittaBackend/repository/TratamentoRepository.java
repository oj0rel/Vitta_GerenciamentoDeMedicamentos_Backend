package com.vitta.vittaBackend.repository;

import com.vitta.vittaBackend.entity.Tratamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TratamentoRepository extends JpaRepository<Tratamento, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Tratamento t SET t.status = 0 WHERE t.id = :id")
    void apagadoLogicoTratamento(@Param("id") Integer tratamentoId);

    @Query("SELECT t FROM Tratamento t WHERE t.status > 0")
    List<Tratamento> listarTratamentos();

    @Query("SELECT t FROM Tratamento t WHERE t.id = :id AND t.status >= 0")
    Tratamento obterTratamentoPeloId(@Param("id") Integer tratamentoId);
}
