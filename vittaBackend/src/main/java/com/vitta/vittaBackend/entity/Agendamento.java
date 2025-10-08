package com.vitta.vittaBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitta.vittaBackend.enums.agendamento.AgendamentoStatus;
import com.vitta.vittaBackend.enums.agendamento.TipoDeAlerta;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agendamento_id")
    private Integer id;

    @Column(name = "agendamento_horario_do_agendamento")
    private LocalDateTime horarioDoAgendamento;

    @Column(name = "agendamento_tipo_de_alerta")
    private TipoDeAlerta tipoDeAlerta;

    @Column(name = "agendamento_status")
    private AgendamentoStatus status = AgendamentoStatus.PENDENTE;

    //isso aki é para trazer a tabela MedicamentoHistorico
    @OneToOne(mappedBy = "agendamento", cascade = CascadeType.ALL)
    private MedicamentoHistorico medicamentoHistorico;

    //isso aki é para enviar a tabela Agendamento para Tratamento
    @ManyToOne
    @JoinColumn(name = "tratamento_id", referencedColumnName = "tratamento_id")
    @JsonIgnore
    private Tratamento tratamento;

    /**
     * O usuário ao qual este agendamento pertence.
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

    public LocalDateTime getHorarioDoAgendamento() {
        return horarioDoAgendamento;
    }

    public void setHorarioDoAgendamento(LocalDateTime horarioDoAgendamento) {
        this.horarioDoAgendamento = horarioDoAgendamento;
    }

    public TipoDeAlerta getTipoDeAlerta() {
        return tipoDeAlerta;
    }

    public void setTipoDeAlerta(TipoDeAlerta tipoDeAlerta) {
        this.tipoDeAlerta = tipoDeAlerta;
    }

    public AgendamentoStatus getStatus() {
        return status;
    }

    public void setStatus(AgendamentoStatus status) {
        this.status = status;
    }

    public MedicamentoHistorico getMedicamentoHistorico() {
        return medicamentoHistorico;
    }

    public void setMedicamentoHistorico(MedicamentoHistorico medicamentoHistorico) {
        this.medicamentoHistorico = medicamentoHistorico;
    }

    public Tratamento getTratamento() {
        return tratamento;
    }

    public void setTratamento(Tratamento tratamento) {
        this.tratamento = tratamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
