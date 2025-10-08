package com.vitta.vittaBackend.dto.response.agendamento;

import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.enums.agendamento.AgendamentoStatus;
import com.vitta.vittaBackend.enums.agendamento.TipoDeAlerta;

import java.time.LocalDateTime;

public class AgendamentoResumoDTOResponse {

    private Integer id;
    private LocalDateTime horarioDoAgendamento;
    private TipoDeAlerta tipoDeAlerta;
    private AgendamentoStatus status;

    public AgendamentoResumoDTOResponse() {
    }

    /**
     * Construtor de mapeamento a partir da entidade.
     * Facilita a conversão da entidade {@link Agendamento} para este DTO de resumo.
     * @param agendamentoEntity A entidade Agendamento vinda do banco de dados.
     */
    public AgendamentoResumoDTOResponse(Agendamento agendamentoEntity) {
        this.id = agendamentoEntity.getId();
        this.horarioDoAgendamento = agendamentoEntity.getHorarioDoAgendamento();
        this.tipoDeAlerta = agendamentoEntity.getTipoDeAlerta();
        this.status = agendamentoEntity.getStatus();
    }

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
}
