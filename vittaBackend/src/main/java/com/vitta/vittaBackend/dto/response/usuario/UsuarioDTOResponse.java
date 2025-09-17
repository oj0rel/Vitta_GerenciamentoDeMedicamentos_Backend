package com.vitta.vittaBackend.dto.response.usuario;

import com.vitta.vittaBackend.dto.response.agendamento.AgendamentoResumoDTOResponse;
import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoResumoDTOResponse;
import com.vitta.vittaBackend.enums.UsuarioStatus;

import java.util.List;

public class UsuarioDTOResponse {

    private Integer id;
    private String nome;
    private String telefone;
    private String email;
    private UsuarioStatus status;

    private List<MedicamentoResumoDTOResponse> medicamentos;
    private List<AgendamentoResumoDTOResponse> agendamentos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UsuarioStatus getStatus() {
        return status;
    }

    public void setStatus(UsuarioStatus status) {
        this.status = status;
    }

    public List<MedicamentoResumoDTOResponse> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<MedicamentoResumoDTOResponse> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public List<AgendamentoResumoDTOResponse> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<AgendamentoResumoDTOResponse> agendamentos) {
        this.agendamentos = agendamentos;
    }
}
