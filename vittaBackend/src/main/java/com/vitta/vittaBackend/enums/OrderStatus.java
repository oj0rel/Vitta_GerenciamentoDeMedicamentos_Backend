package com.vitta.vittaBackend.enums;

public enum OrderStatus {
    ATIVO(1),
    INATIVO(0),
    CANCELADO(-1);

    private Integer code;

    OrderStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static OrderStatus fromCodigo(Integer code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + code);
    }
}
