package com.vitta.vittaBackend.dto.response.agendamento;

import com.vitta.vittaBackend.dto.response.medicamentoHistorico.MedicamentoHistoricoResumoDTOResponse;
import com.vitta.vittaBackend.dto.response.usuario.UsuarioResumoDTOResponse;
import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.enums.agendamento.AgendamentoStatus;
import com.vitta.vittaBackend.enums.agendamento.TipoDeAlerta;

import java.time.Instant;
import java.time.LocalDateTime;

public class AgendamentoDTOResponse {

    private Integer id;
    private LocalDateTime horarioDoAgendamento;
    private TipoDeAlerta tipoDeAlerta;
    private AgendamentoStatus status;
    private UsuarioResumoDTOResponse usuario;
    private MedicamentoHistoricoResumoDTOResponse historicoDoMedicamentoTomado;

    public AgendamentoDTOResponse() {
    }

    /**
     * Construtor de mapeamento a partir da entidade Agendamento.
     * @param agendamentoEntity A entidade vinda do banco de dados.
     */
    public AgendamentoDTOResponse(Agendamento agendamentoEntity) {
        this.id = agendamentoEntity.getId();
        this.horarioDoAgendamento = agendamentoEntity.getHorarioDoAgendamento();
        this.tipoDeAlerta = agendamentoEntity.getTipoDeAlerta();
        this.status = agendamentoEntity.getStatus();

        if (agendamentoEntity.getUsuario() != null) {
            this.usuario = new UsuarioResumoDTOResponse(agendamentoEntity.getUsuario());
        }

        if (agendamentoEntity.getMedicamentoHistorico() != null) {
            this.historicoDoMedicamentoTomado = new MedicamentoHistoricoResumoDTOResponse(agendamentoEntity.getMedicamentoHistorico());
        }
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

    public UsuarioResumoDTOResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResumoDTOResponse usuario) {
        this.usuario = usuario;
    }

    public MedicamentoHistoricoResumoDTOResponse getHistoricoDoMedicamentoTomado() {
        return historicoDoMedicamentoTomado;
    }

    public void setHistoricoDoMedicamentoTomado(MedicamentoHistoricoResumoDTOResponse historicoDoMedicamentoTomado) {
        this.historicoDoMedicamentoTomado = historicoDoMedicamentoTomado;
    }
}
