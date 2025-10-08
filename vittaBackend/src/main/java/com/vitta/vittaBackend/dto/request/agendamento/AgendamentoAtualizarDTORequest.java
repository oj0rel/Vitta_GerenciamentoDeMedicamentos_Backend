package com.vitta.vittaBackend.dto.request.agendamento;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class AgendamentoAtualizarDTORequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime horarioDoAgendamento;
    private Integer tipoDeAlerta;
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
