package com.vitta.vittaBackend.enums;

public enum TipoFrequencia {
    INTERVALO_HORAS(1),
    HORARIOS_ESPECIFICOS(2);

    private final int codigo;

    TipoFrequencia(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TipoFrequencia fromCodigo(int codigo) {
        for (TipoFrequencia tipo : TipoFrequencia.values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de frequência inválido: " + codigo);
    }
}
