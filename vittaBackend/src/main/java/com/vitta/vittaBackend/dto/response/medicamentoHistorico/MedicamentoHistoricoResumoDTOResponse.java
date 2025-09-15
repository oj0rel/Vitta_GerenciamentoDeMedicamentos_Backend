package com.vitta.vittaBackend.dto.response.medicamentoHistorico;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MedicamentoHistoricoResumoDTOResponse {
    private Integer id;
    private LocalDateTime horaDoUso;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getHoraDoUso() {
        return horaDoUso;
    }

    public void setHoraDoUso(LocalDateTime horaDoUso) {
        this.horaDoUso = horaDoUso;
    }

}
