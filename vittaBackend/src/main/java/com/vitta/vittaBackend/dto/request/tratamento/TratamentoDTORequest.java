package com.vitta.vittaBackend.dto.request.tratamento;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TratamentoDTORequest {

    private Integer medicamentoId;
    private Integer usuarioId;
    private BigDecimal dosagem;
    private String instrucoes;
    private LocalDate dataDeInicio;
    private LocalDate dataDeTermino;
    private Integer tipoDeFrequencia;
    private Integer intervaloEmHoras;
    private String horariosEspecificos;

    public Integer getMedicamentoId() {
        return medicamentoId;
    }

    public void setMedicamentoId(Integer medicamentoId) {
        this.medicamentoId = medicamentoId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public BigDecimal getDosagem() {
        return dosagem;
    }

    public void setDosagem(BigDecimal dosagem) {
        this.dosagem = dosagem;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }

    public LocalDate getDataDeInicio() {
        return dataDeInicio;
    }

    public void setDataDeInicio(LocalDate dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public LocalDate getDataDeTermino() {
        return dataDeTermino;
    }

    public void setDataDeTermino(LocalDate dataDeTermino) {
        this.dataDeTermino = dataDeTermino;
    }

    public Integer getTipoDeFrequencia() {
        return tipoDeFrequencia;
    }

    public void setTipoDeFrequencia(Integer tipoDeFrequencia) {
        this.tipoDeFrequencia = tipoDeFrequencia;
    }

    public Integer getIntervaloEmHoras() {
        return intervaloEmHoras;
    }

    public void setIntervaloEmHoras(Integer intervaloEmHoras) {
        this.intervaloEmHoras = intervaloEmHoras;
    }

    public String getHorariosEspecificos() {
        return horariosEspecificos;
    }

    public void setHorariosEspecificos(String horariosEspecificos) {
        this.horariosEspecificos = horariosEspecificos;
    }
}
