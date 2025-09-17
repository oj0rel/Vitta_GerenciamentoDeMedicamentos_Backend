package com.vitta.vittaBackend.entity;

import com.vitta.vittaBackend.enums.GeralStatus;
import com.vitta.vittaBackend.enums.agendamento.TipoDeAlerta;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agendamento_id")
    private Integer id;

    @Column(name = "agendamento_horario_do_agendamento")
    private Instant horarioDoAgendamento;

    @Column(name = "agendamento_tipo_de_alerta")
    private TipoDeAlerta tipoDeAlerta;

    @Column(name = "agendamento_status")
    private GeralStatus status = GeralStatus.ATIVO;

    //isso aki é para enviar a tabela Agendamento para Medicamento
    @ManyToOne
    @JoinColumn(name = "medicamento_id")
    private Medicamento medicamento;

    //isso aki é para enviar a tabela Agendamento para Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getHorarioDoAgendamento() {
        return horarioDoAgendamento;
    }

    public void setHorarioDoAgendamento(Instant horarioDoAgendamento) {
        this.horarioDoAgendamento = horarioDoAgendamento;
    }

    public TipoDeAlerta getTipoDeAlerta() {
        return tipoDeAlerta;
    }

    public void setTipoDeAlerta(TipoDeAlerta tipoDeAlerta) {
        this.tipoDeAlerta = tipoDeAlerta;
    }

    public GeralStatus getStatus() {
        return status;
    }

    public void setStatus(GeralStatus status) {
        this.status = status;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
