package com.vitta.vittaBackend.enums.agendamento;

import com.vitta.vittaBackend.enums.medicamento.TipoUnidadeDeMedida;

public enum TipoDeAlerta {
    NOTIFICACAO_PUSH(1),
    ALARME(2);

    private final int codigo;

    TipoDeAlerta(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TipoDeAlerta fromCodigo(int codigo) {
        for (TipoDeAlerta t : values()) {
            if (t.getCodigo() == codigo) {
                return t;
            }
        }
        throw new IllegalArgumentException("Código inválido para TipoDeAlerta: " + codigo);
    }
}
