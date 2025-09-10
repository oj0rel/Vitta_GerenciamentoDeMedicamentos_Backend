package com.vitta.vittaBackend.enums;

public enum OrderStatus {
    ATIVO(1),
    INATIVO(0),
    CANCELADO(-1);

    private int code;

    private OrderStatus(Integer code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderStatus fromCodigo(int code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + code);
    }
}
