package com.vitta.vittaBackend.enums;

public enum GeralStatus {
    ATIVO(1),
    INATIVO(0);

    private final Integer code;

    GeralStatus(Integer code) { this.code = code; }

    public Integer getCode() {
        return code;
    }

    public static GeralStatus fromCodigo(int codigo) {
        for (GeralStatus u : values()) {
            if (u.getCode() == codigo) {
                return u;
            }
        }
        throw new IllegalArgumentException("Código inválido para Status: " + codigo);
    }
}
