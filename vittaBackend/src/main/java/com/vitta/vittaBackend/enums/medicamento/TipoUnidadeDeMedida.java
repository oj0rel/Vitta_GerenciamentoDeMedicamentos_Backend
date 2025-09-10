package com.vitta.vittaBackend.enums.medicamento;

public enum TipoUnidadeDeMedida {
    MG(1),
    G(2),
    ML(3),
    GOTAS(4),
    COMPRIMIDOS(5),
    CAPSULAS(6);

    private final int codigo;

    TipoUnidadeDeMedida(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TipoUnidadeDeMedida fromCodigo(int codigo) {
        for (TipoUnidadeDeMedida t : values()) {
            if (t.getCodigo() == codigo) {
                return t;
            }
        }
        throw new IllegalArgumentException("Código inválido para TipoUnidadeDeMedida: " + codigo);
    }

}
