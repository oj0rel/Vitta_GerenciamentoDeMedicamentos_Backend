package com.vitta.vittaBackend.repository;

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

    /**
     * Retorna uma lista de todos os registros de histórico ativos de um usuário específico.
     * A consulta navega de MedicamentoHistorico -> Medicamento -> Usuario para filtrar pelo ID do usuário.
     *
     * @param usuarioId O ID do usuário para o qual o histórico será listado.
     * @return Uma lista de entidades {@link MedicamentoHistorico} ativas para o usuário especificado.
     */
    @Query("SELECT m FROM MedicamentoHistorico m WHERE m.usuario.id = :usuarioId AND m.historicoStatus > 0")
    List<MedicamentoHistorico> listarMedicamentosHistoricos(@Param("usuarioId") Integer usuarioId);

    /**
     * Busca um registro de histórico específico pelo seu ID e pelo ID do usuário proprietário.
     * Garante que um usuário só possa acessar seus próprios registros de histórico.
     *
     * @param historicoId O ID do registro de histórico a ser buscado.
     * @param usuarioId O ID do usuário que deve ser o proprietário do medicamento associado.
     */
    @Query("SELECT m FROM MedicamentoHistorico m WHERE m.id = :historicoId AND m.usuario.id = :usuarioId AND m.historicoStatus >= 0")
    MedicamentoHistorico listarMedicamentoHistoricoPorId(@Param("historicoId") Integer historicoId, @Param("usuarioId") Integer usuarioId);

    /**
     * Realiza a exclusão lógica de um registro de histórico, garantindo que ele pertença ao usuário correto.
     * Esta consulta de atualização (UPDATE) altera o status do histórico para 0 (inativo).
     *
     * @param historicoId O ID do registro de histórico a ser desativado.
     * @param usuarioId O ID do usuário proprietário do medicamento associado.
     */
    @Modifying
    @Transactional
    @Query("UPDATE MedicamentoHistorico m SET m.historicoStatus = 0 WHERE m.id = :historicoId AND m.usuario.id = :usuarioId")
    void apagarLogicoMedicamentoHistorico(@Param("historicoId") Integer historicoId, @Param("usuarioId") Integer usuarioId);
}
