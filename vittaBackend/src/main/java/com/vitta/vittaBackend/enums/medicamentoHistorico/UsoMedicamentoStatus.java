package com.vitta.vittaBackend.enums.medicamentoHistorico;

public enum UsoMedicamentoStatus {
    ATIVO(1),
    INATIVO(0);

    private final Integer code;

    UsoMedicamentoStatus(Integer code) { this.code = code; }

    public Integer getCode() {
        return code;
    }

    public static UsoMedicamentoStatus fromCodigo(int codigo) {
        for (UsoMedicamentoStatus u : values()) {
            if (u.getCode() == codigo) {
                return u;
            }
        }
        throw new IllegalArgumentException("Código inválido para UsoMedicamentoStatus: " + codigo);
    }
}
