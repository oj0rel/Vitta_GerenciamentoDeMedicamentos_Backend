package com.vitta.vittaBackend.dto.response.agendamento;

import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoResumoDTOResponse;
import com.vitta.vittaBackend.dto.response.medicamentoHistorico.MedicamentoHistoricoDTOResponse;
import com.vitta.vittaBackend.dto.response.usuario.UsuarioResumoDTOResponse;
import com.vitta.vittaBackend.enums.GeralStatus;
import com.vitta.vittaBackend.enums.agendamento.AgendamentoStatus;
import com.vitta.vittaBackend.enums.agendamento.TipoDeAlerta;

import java.time.Instant;
import java.time.LocalDateTime;

public class AgendamentoDTOResponse {

    private Integer id;
    private LocalDateTime horarioDoAgendamento;
    private TipoDeAlerta tipoDeAlerta;
    private AgendamentoStatus status;
    private MedicamentoResumoDTOResponse medicamento;
    private UsuarioResumoDTOResponse usuario;
    private MedicamentoHistoricoDTOResponse historico;


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

    public MedicamentoResumoDTOResponse getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(MedicamentoResumoDTOResponse medicamento) {
        this.medicamento = medicamento;
    }

    public UsuarioResumoDTOResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResumoDTOResponse usuario) {
        this.usuario = usuario;
    }

    public MedicamentoHistoricoDTOResponse getHistorico() {
        return historico;
    }

    public void setHistorico(MedicamentoHistoricoDTOResponse historico) {
        this.historico = historico;
    }
}
