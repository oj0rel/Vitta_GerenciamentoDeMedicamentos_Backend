package com.vitta.vittaBackend.dto.response.usuario;

import com.vitta.vittaBackend.enums.UsuarioStatus;

public class UsuarioResumoDTOResponse {

    private Integer id;
    private String nome;
    private UsuarioStatus status;

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

    public UsuarioStatus getStatus() {
        return status;
    }

    public void setStatus(UsuarioStatus status) {
        this.status = status;
    }
}
