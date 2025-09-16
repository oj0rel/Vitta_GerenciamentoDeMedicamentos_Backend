package com.vitta.vittaBackend.dto.response.agendamento;

import com.vitta.vittaBackend.dto.response.medicamentoHistorico.MedicamentoHistoricoResumoDTOResponse;
import com.vitta.vittaBackend.dto.response.usuario.UsuarioResumoDTOResponse;
import com.vitta.vittaBackend.enums.OrderStatus;
import com.vitta.vittaBackend.enums.agendamento.TipoDeAlerta;

import java.time.Instant;
import java.util.List;

public class AgendamentoDTOResponse {

    private Instant horarioDoAgendamento;
    private TipoDeAlerta tipoDeAlerta;
    private OrderStatus status;
    private List<MedicamentoHistoricoResumoDTOResponse> medicamentosHistoricos;
    private UsuarioResumoDTOResponse usuario;


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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<MedicamentoHistoricoResumoDTOResponse> getMedicamentosHistoricos() {
        return medicamentosHistoricos;
    }

    public void setMedicamentosHistoricos(List<MedicamentoHistoricoResumoDTOResponse> medicamentosHistoricos) {
        this.medicamentosHistoricos = medicamentosHistoricos;
    }

    public UsuarioResumoDTOResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResumoDTOResponse usuario) {
        this.usuario = usuario;
    }
}
