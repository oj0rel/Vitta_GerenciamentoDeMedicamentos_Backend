package com.vitta.vittaBackend.dto.response.medicamento;

import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.enums.GeralStatus;

public class MedicamentoResumoDTOResponse {
    private Integer id;
    private String nome;
    private GeralStatus status;

    public MedicamentoResumoDTOResponse() {
    }

    public MedicamentoResumoDTOResponse(Medicamento medicamentoEntity) {
        this.id = medicamentoEntity.getId();
        this.nome = medicamentoEntity.getNome();
        this.status = medicamentoEntity.getStatus();
    }

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

    public GeralStatus getStatus() {
        return status;
    }

    public void setStatus(GeralStatus status) {
        this.status = status;
    }
}
