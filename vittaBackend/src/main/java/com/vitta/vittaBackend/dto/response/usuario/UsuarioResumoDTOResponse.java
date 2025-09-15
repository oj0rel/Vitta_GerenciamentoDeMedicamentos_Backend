package com.vitta.vittaBackend.dto.response.usuario;

import com.vitta.vittaBackend.enums.OrderStatus;

public class UsuarioResumoDTOResponse {

    private Integer id;
    private String nome;
    private OrderStatus status;

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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
