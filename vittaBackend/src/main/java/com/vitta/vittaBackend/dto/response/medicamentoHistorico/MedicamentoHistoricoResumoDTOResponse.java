package com.vitta.vittaBackend.dto.response.medicamentoHistorico;

import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoResumoDTOResponse;
import com.vitta.vittaBackend.entity.MedicamentoHistorico;
import com.vitta.vittaBackend.enums.GeralStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MedicamentoHistoricoResumoDTOResponse {
    private Integer id;
    private LocalDateTime horaDoUso;
    private BigDecimal doseTomada;
    private String observacao;
    private GeralStatus historicoStatus;

    public MedicamentoHistoricoResumoDTOResponse() {
    }

    public MedicamentoHistoricoResumoDTOResponse(MedicamentoHistorico historicoEntity) {
        this.id = historicoEntity.getId();
        this.horaDoUso = historicoEntity.getHoraDoUso();
        this.doseTomada = historicoEntity.getDoseTomada();
        this.observacao = historicoEntity.getObservacao();
        this.historicoStatus = historicoEntity.getHistoricoStatus();
    }

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

    public GeralStatus getHistoricoStatus() {
        return historicoStatus;
    }

    public void setHistoricoStatus(GeralStatus historicoStatus) {
        this.historicoStatus = historicoStatus;
    }
}
