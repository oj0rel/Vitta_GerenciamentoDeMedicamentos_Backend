package com.vitta.vittaBackend.dto.request.medicamentoHistorico;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RegistrarUsoDTORequest {

    private LocalDateTime horaDoUso = LocalDateTime.now();
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
