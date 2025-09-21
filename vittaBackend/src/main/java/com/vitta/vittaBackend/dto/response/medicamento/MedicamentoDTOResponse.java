package com.vitta.vittaBackend.dto.response.medicamento;

import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.enums.GeralStatus;
import com.vitta.vittaBackend.enums.medicamento.TipoUnidadeDeMedida;

/**
 * DTO para representar a resposta completa dos dados de um Medicamento.
 * Usado em listagens ou ao buscar um medicamento específico.
 */
public class MedicamentoDTOResponse {

    /**
     * O ID único do medicamento no banco de dados.
     */
    private Integer id;

    /**
     * O nome comercial do medicamento.
     */
    private String nome;

    /**
     * O princípio ativo do medicamento.
     */
    private String principioAtivo;

    /**
     * O laboratório fabricante do medicamento.
     */
    private String laboratorio;

    /**
     * A forma farmacêutica do medicamento (ex: COMPRIMIDO, CAPSULA).
     */
    private TipoUnidadeDeMedida tipoUnidadeDeMedida;

    /**
     * O status atual do medicamento no catálogo (ex: ATIVO, INATIVO).
     */
    private GeralStatus status;

    /**
     * Construtor vazio.
     * Necessário para compatibilidade com alguns frameworks de serialização.
     */
    public MedicamentoDTOResponse() {
    }

    /**
     * Construtor de mapeamento.
     * Facilita a conversão da entidade Medicamento para este DTO.
     * @param medicamentoEntity A entidade vinda do banco de dados.
     */
    public MedicamentoDTOResponse(Medicamento medicamentoEntity) {
        this.id = medicamentoEntity.getId();
        this.nome = medicamentoEntity.getNome();
        this.principioAtivo = medicamentoEntity.getPrincipioAtivo();
        this.laboratorio = medicamentoEntity.getLaboratorio();
        this.tipoUnidadeDeMedida = medicamentoEntity.getTipoUnidadeDeMedida();
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

    public TipoUnidadeDeMedida getTipoUnidadeDeMedida() {
        return tipoUnidadeDeMedida;
    }

    public void setTipoUnidadeDeMedida(TipoUnidadeDeMedida tipoUnidadeDeMedida) {
        this.tipoUnidadeDeMedida = tipoUnidadeDeMedida;
    }

    public GeralStatus getStatus() {
        return status;
    }

    public void setStatus(GeralStatus status) {
        this.status = status;
    }
}
