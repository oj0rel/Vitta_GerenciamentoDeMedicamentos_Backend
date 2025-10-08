package com.vitta.vittaBackend.dto.response.agenda;

import com.vitta.vittaBackend.dto.response.medicamentoHistorico.MedicamentoHistoricoDTOResponse;
import com.vitta.vittaBackend.enums.agendamento.AgendamentoStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AgendaDoDiaDTOResponse {

    private Integer agendamentoId;
    private LocalDateTime horario;
    private String nomeMedicamento;
    private BigDecimal dosagem;
    private String instrucoes;
    private MedicamentoHistoricoDTOResponse historico;
    private AgendamentoStatus statusDoAgendamento;

    public Integer getAgendamentoId() {
        return agendamentoId;
    }

    public void setAgendamentoId(Integer agendamentoId) {
        this.agendamentoId = agendamentoId;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }

    public BigDecimal getDosagem() {
        return dosagem;
    }

    public void setDosagem(BigDecimal dosagem) {
        this.dosagem = dosagem;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }

    public AgendamentoStatus getStatusDoAgendamento() {
        return statusDoAgendamento;
    }

    public void setStatusDoAgendamento(AgendamentoStatus statusDoAgendamento) {
        this.statusDoAgendamento = statusDoAgendamento;
    }

    public MedicamentoHistoricoDTOResponse getHistorico() {
        return historico;
    }

    public void setHistorico(MedicamentoHistoricoDTOResponse historico) {
        this.historico = historico;
    }
}
