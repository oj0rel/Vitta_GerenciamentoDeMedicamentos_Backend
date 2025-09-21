package com.vitta.vittaBackend.dto.response.medicamento;

import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.enums.GeralStatus;

/**
 * DTO para representar uma visão simplificada de um Medicamento.
 * Ideal para ser aninhado em outras respostas de DTO (ex: dentro de um Tratamento)
 * para evitar o excesso de informações.
 */
public class MedicamentoResumoDTOResponse {

    /**
     * O ID único do medicamento.
     */
    private Integer id;

    /**
     * O nome comercial do medicamento.
     */
    private String nome;

    /**
     * O status atual do medicamento (ATIVO ou INATIVO).
     */
    private GeralStatus status;

    /**
     * Construtor vazio.
     */
    public MedicamentoResumoDTOResponse() {
    }

    /**
     * Construtor de mapeamento a partir da entidade.
     * Facilita a conversão da entidade {@link Medicamento} para este DTO de resumo.
     * @param medicamentoEntity A entidade vinda do banco de dados.
     */
    public MedicamentoResumoDTOResponse(Medicamento medicamentoEntity) {
        this.id = medicamentoEntity.getId();
        this.nome = medicamentoEntity.getNome();
        this.status = medicamentoEntity.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public GeralStatus getStatus() {
        return status;
    }

    public void setStatus(GeralStatus status) {
        this.status = status;
    }
}
