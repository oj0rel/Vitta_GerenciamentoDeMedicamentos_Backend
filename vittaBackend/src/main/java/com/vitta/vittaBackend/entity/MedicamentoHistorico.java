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

    //isso aki é para enviar a tabela MedicamentoHistorico para Agendamento
    @OneToOne
    @JoinColumn(name = "agendamento_id", referencedColumnName = "agendamento_id")
    @JsonIgnore
    private Agendamento agendamento;

    /**
     * O usuário ao qual este histórico pertence.
     * Relacionamento Muitos-para-Um com a entidade Usuario.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id")
    private Usuario usuario;

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

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
