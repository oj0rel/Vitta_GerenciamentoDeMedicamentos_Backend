package com.vitta.vittaBackend.dto.request.agendamento;

import java.time.LocalDateTime;

public class AgendamentoDTORequest {

    private LocalDateTime horarioDoAgendamento;
    private Integer tipoDeAlerta;
    private Integer tratamentoId;

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

}
