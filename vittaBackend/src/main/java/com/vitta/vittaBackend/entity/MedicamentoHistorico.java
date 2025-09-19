package com.vitta.vittaBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitta.vittaBackend.enums.GeralStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "uso_de_medicamento_historico")
public class MedicamentoHistorico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uso_de_medicamento_historico_id")
    private Integer id;

    @Column(name = "uso_de_medicamento_historico_data_hora_do_uso")
    private LocalDateTime horaDoUso;

    @Column(name = "uso_de_medicamento_historico_dose_tomada")
    private BigDecimal doseTomada;

    @Column(name = "uso_de_medicamento_historico_observacao")
    private String observacao;

    @Column(name = "uso_de_medicamento_historico_status")
    private GeralStatus historicoStatus = GeralStatus.ATIVO;

    //isso aki é para enviar a tabela UsoMedicamentoHistorico para Medicamento
    @ManyToOne
    @JoinColumn(name = "medicamento_id")
    private Medicamento medicamento;

    @OneToOne
    @JoinColumn(name = "agendamento_id", referencedColumnName = "agendamento_id")
    @JsonIgnore
    private Agendamento agendamento;

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

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }
}
