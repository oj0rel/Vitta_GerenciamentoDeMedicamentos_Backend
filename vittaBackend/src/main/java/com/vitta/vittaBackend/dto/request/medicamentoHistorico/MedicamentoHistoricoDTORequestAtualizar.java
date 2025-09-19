package com.vitta.vittaBackend.dto.request.medicamentoHistorico;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MedicamentoHistoricoDTORequestAtualizar {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime horaDoUso;

    private BigDecimal doseTomada;
    private String observacao;

    public LocalDateTime getHoraDoUso() {
        return horaDoUso;
    }

    public void setHoraDoUso(LocalDateTime horaDoUso) {
        this.horaDoUso = horaDoUso;
    }

    public BigDecimal getDoseTomada() {
        return doseTomada;
    }

    public void setDoseTomada(BigDecimal doseTomada) {
        this.doseTomada = doseTomada;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
