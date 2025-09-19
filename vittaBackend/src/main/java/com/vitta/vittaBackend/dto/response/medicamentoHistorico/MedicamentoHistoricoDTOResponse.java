package com.vitta.vittaBackend.dto.response.medicamentoHistorico;

import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoResumoDTOResponse;
import com.vitta.vittaBackend.entity.MedicamentoHistorico;
import com.vitta.vittaBackend.enums.GeralStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MedicamentoHistoricoDTOResponse {
    private Integer id;
    private LocalDateTime horaDoUso;
    private BigDecimal doseTomada;
    private String observacao;
    private GeralStatus historicoStatus;
    private MedicamentoResumoDTOResponse medicamento;

    public MedicamentoHistoricoDTOResponse() {
    }

    public MedicamentoHistoricoDTOResponse(MedicamentoHistorico historicoEntity) {
        this.id = historicoEntity.getId();
        this.horaDoUso = historicoEntity.getHoraDoUso();
        this.doseTomada = historicoEntity.getDoseTomada();
        this.observacao = historicoEntity.getObservacao();
        this.historicoStatus = historicoEntity.getHistoricoStatus();

        // Converte também o medicamento aninhado
        if (historicoEntity.getMedicamento() != null) {
            this.medicamento = new MedicamentoResumoDTOResponse(historicoEntity.getMedicamento());
        }
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

    public MedicamentoResumoDTOResponse getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(MedicamentoResumoDTOResponse medicamento) {
        this.medicamento = medicamento;
    }
}
