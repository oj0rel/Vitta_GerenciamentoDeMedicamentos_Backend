package com.vitta.vittaBackend.dto.request.agendamento;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.time.LocalDateTime;

public class AgendamentoDTORequest {

    private LocalDateTime horarioDoAgendamento;
    private Integer tipoDeAlerta;
    private Integer tratamentoId;
    private Integer usuarioId;

    public LocalDateTime getHorarioDoAgendamento() {
        return horarioDoAgendamento;
    }

    public void setHorarioDoAgendamento(LocalDateTime horarioDoAgendamento) {
        this.horarioDoAgendamento = horarioDoAgendamento;
    }

    public Integer getTipoDeAlerta() {
        return tipoDeAlerta;
    }

    public void setTipoDeAlerta(Integer tipoDeAlerta) {
        this.tipoDeAlerta = tipoDeAlerta;
    }

    public Integer getTratamentoId() {
        return tratamentoId;
    }

    public void setTratamentoId(Integer tratamentoId) {
        this.tratamentoId = tratamentoId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

}
