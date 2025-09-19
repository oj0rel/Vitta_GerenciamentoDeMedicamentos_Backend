package com.vitta.vittaBackend.dto.response.usuario;

import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.UsuarioStatus;

public class UsuarioResumoDTOResponse {

    private Integer id;
    private String nome;
    private UsuarioStatus status;

    public UsuarioResumoDTOResponse() {
    }

    public UsuarioResumoDTOResponse(Usuario usuarioEntity) {
        this.id = usuarioEntity.getId();
        this.nome = usuarioEntity.getNome();
        this.status = usuarioEntity.getStatus();
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

    public UsuarioStatus getStatus() {
        return status;
    }

    public void setStatus(UsuarioStatus status) {
        this.status = status;
    }
}
