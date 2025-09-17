package com.vitta.vittaBackend.enums.agendamento;

public enum AgendamentoStatus {

    PENDENTE(0),
    TOMADO(1),
    ATRASADO(2);

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
