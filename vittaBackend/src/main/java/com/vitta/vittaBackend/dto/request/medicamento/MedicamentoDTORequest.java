package com.vitta.vittaBackend.dto.request.medicamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para receber os dados na criação de um novo Medicamento no catálogo.
 */
public class MedicamentoDTORequest {

    /**
     * Nome comercial ou popular do medicamento.
     * Campo obrigatório.
     */
    @NotBlank(message = "O nome do medicamento não pode ser vazio.")
    private String nome;

    /**
     * Nome da substância farmacológica principal do medicamento.
     * Campo obrigatório.
     */
    @NotBlank(message = "O princípio ativo não pode ser vazio.")
    private String principioAtivo;

    /**
     * Nome do laboratório ou fabricante do medicamento.
     * Campo opcional.
     */
    private String laboratorio;

    /**
     * Código numérico que representa a forma do medicamento (ex: 1 para Comprimido, 2 para Cápsula).
     * @see com.vitta.vittaBackend.enums.medicamento.TipoUnidadeDeMedida
     */
    @NotNull
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
