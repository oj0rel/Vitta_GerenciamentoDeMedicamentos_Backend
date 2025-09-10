package com.vitta.vittaBackend.dto.request.usuario;

import java.util.List;

public class UsuarioDTORequestAtualizar {
    private String nome;
    private String telefone;
    private List<Integer> medicamentosId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Integer> getMedicamentosId() {
        return medicamentosId;
    }

    public void setMedicamentosId(List<Integer> medicamentosId) {
        this.medicamentosId = medicamentosId;
    }
}
