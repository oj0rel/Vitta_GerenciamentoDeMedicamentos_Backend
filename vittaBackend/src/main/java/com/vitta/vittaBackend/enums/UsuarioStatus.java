package com.vitta.vittaBackend.enums;

public enum UsuarioStatus {
    ATIVO(1),
    INATIVO(0),
    CANCELADO(-1);

    private Integer code;

    UsuarioStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static UsuarioStatus fromCodigo(Integer code) {
        for (UsuarioStatus status : UsuarioStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + code);
    }
}
