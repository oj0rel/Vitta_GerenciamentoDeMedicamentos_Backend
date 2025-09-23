package com.vitta.vittaBackend.dto.request.medicamento;

/**
 * DTO para receber os dados na atualização de um Medicamento existente.
 * Todos os campos são opcionais, permitindo a atualização parcial do recurso.
 */
public class MedicamentoAtualizarDTORequest {

    /**
     * O novo nome comercial do medicamento.
     */
    private String nome;

    /**
     * O novo princípio ativo do medicamento.
     */
    private String principioAtivo;

    /**
     * O novo laboratório do medicamento.
     */
    private String laboratorio;

    /**
     * O novo código da unidade de medida do medicamento.
     */
    private Integer tipoUnidadeDeMedida;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public void setPrincipioAtivo(String principioAtivo) {
        this.principioAtivo = principioAtivo;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Integer getTipoUnidadeDeMedida() {
        return tipoUnidadeDeMedida;
    }

    public void setTipoUnidadeDeMedida(Integer tipoUnidadeDeMedida) {
        this.tipoUnidadeDeMedida = tipoUnidadeDeMedida;
    }
}
