package com.vitta.vittaBackend.dto.request.agendamento;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public class AgendamentoAtualizarDTORequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant horarioDoAgendamento;
    private Integer tipoDeAlerta;

    private Integer status;

    public Instant getHorarioDoAgendamento() {
        return horarioDoAgendamento;
    }

    public void setHorarioDoAgendamento(Instant horarioDoAgendamento) {
        this.horarioDoAgendamento = horarioDoAgendamento;
    }

    public Integer getTipoDeAlerta() {
        return tipoDeAlerta;
    }

    public void setTipoDeAlerta(Integer tipoDeAlerta) {
        this.tipoDeAlerta = tipoDeAlerta;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
