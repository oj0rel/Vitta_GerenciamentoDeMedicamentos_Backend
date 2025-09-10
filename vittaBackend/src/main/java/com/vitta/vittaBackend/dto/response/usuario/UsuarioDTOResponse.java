package com.vitta.vittaBackend.dto.response.usuario;

import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoDTOResponse;
import com.vitta.vittaBackend.enums.OrderStatus;

import java.util.List;

public class UsuarioDTOResponse {
    private Integer id;
    private String nome;
    private String telefone;
    private String email;
    private Integer status;

    private List<MedicamentoDTOResponse> medicamentos;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<MedicamentoDTOResponse> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<MedicamentoDTOResponse> medicamentos) {
        this.medicamentos = medicamentos;
    }
}
