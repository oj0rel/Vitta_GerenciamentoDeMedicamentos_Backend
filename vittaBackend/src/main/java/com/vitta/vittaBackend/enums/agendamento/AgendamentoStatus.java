package com.vitta.vittaBackend.enums.agendamento;

public enum AgendamentoStatus {

    INATIVO(0),
    PENDENTE(1),
    TOMADO(2),
    ATRASADO(3);

    private final int codigo;

    AgendamentoStatus(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static AgendamentoStatus fromCodigo(int codigo) {
        for (AgendamentoStatus a : values()) {
            if (a.getCodigo() == codigo) {
                return a;
            }
        }
        throw new IllegalArgumentException("Código inválido para Status do Agendamento: " + codigo);
    }
}
