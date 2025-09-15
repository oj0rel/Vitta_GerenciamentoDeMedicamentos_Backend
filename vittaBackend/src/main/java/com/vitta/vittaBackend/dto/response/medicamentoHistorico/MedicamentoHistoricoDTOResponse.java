package com.vitta.vittaBackend.dto.response.medicamentoHistorico;

import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoDTOResponse;
import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoResumoDTOResponse;
import com.vitta.vittaBackend.enums.medicamentoHistorico.UsoMedicamentoStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MedicamentoHistoricoDTOResponse {
    private Integer id;
    private LocalDateTime horaDoUso;
    private BigDecimal doseTomada;
    private String observacao;
    private UsoMedicamentoStatus historicoStatus;
    private MedicamentoResumoDTOResponse medicamento;

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

    public UsoMedicamentoStatus getHistoricoStatus() {
        return historicoStatus;
    }

    public void setHistoricoStatus(UsoMedicamentoStatus historicoStatus) {
        this.historicoStatus = historicoStatus;
    }

    public MedicamentoResumoDTOResponse getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(MedicamentoResumoDTOResponse medicamento) {
        this.medicamento = medicamento;
    }
}
