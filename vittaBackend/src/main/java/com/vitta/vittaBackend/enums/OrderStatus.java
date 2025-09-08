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

    public static OrderStatus fromCode(Integer code) {
        for (OrderStatus value : OrderStatus.values()) {
            if (value.code == code) {
                return value;
            }
        }

        throw new IllegalArgumentException("Invalid OrderStatus code");
    }
}
