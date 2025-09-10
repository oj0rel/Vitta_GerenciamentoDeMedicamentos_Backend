package com.vitta.vittaBackend.dto.request.medicamento;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vitta.vittaBackend.enums.medicamento.TipoUnidadeDeMedida;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class MedicamentoDTORequest {
    private String nome;
    private BigDecimal dosagem;
    private Integer tipoUnidadeDeMedida;
    private Integer frequencia;
    private String instrucoes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime dataDeInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private LocalDateTime dataDeTermino;

    private Integer usuarioId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getDosagem() {
        return dosagem;
    }

    public void setDosagem(BigDecimal dosagem) {
        this.dosagem = dosagem;
    }

    public Integer getTipoUnidadeDeMedida() {
        return tipoUnidadeDeMedida;
    }

    public void setTipoUnidadeDeMedida(Integer tipoUnidadeDeMedida) {
        this.tipoUnidadeDeMedida = tipoUnidadeDeMedida;
    }

    public Integer getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(Integer frequencia) {
        this.frequencia = frequencia;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }

    public LocalDateTime getDataDeInicio() {
        return dataDeInicio;
    }

    public void setDataDeInicio(LocalDateTime dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public LocalDateTime getDataDeTermino() {
        return dataDeTermino;
    }

    public void setDataDeTermino(LocalDateTime dataDeTermino) {
        this.dataDeTermino = dataDeTermino;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
