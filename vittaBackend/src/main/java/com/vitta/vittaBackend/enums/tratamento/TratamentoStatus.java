package com.vitta.vittaBackend.enums.tratamento;

public enum TratamentoStatus {
    ATIVO(1),
    PAUSADO(2),
    CONCLUIDO(3),
    CANCELADO(0);

    private final int codigo;

    TratamentoStatus(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static TratamentoStatus fromCodigo(int codigo) {
        for (TratamentoStatus tipoStatus : TratamentoStatus.values()) {
            if (tipoStatus.getCodigo() == codigo) {
                return tipoStatus;
            }
        }
        throw new IllegalArgumentException("Código de frequência inválido: " + codigo);
    }

}
