package com.vitta.vittaBackend.dto.response.medicamento;

import com.vitta.vittaBackend.dto.response.medicamentoHistorico.MedicamentoHistoricoResumoDTOResponse;
import com.vitta.vittaBackend.dto.response.usuario.UsuarioDTOResponse;
import com.vitta.vittaBackend.enums.OrderStatus;
import com.vitta.vittaBackend.enums.medicamento.TipoUnidadeDeMedida;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class MedicamentoDTOResponse {
    private Integer id;
    private String nome;
    private BigDecimal dosagem;
    private TipoUnidadeDeMedida tipoUnidadeDeMedida;
    private Integer frequencia;
    private String instrucoes;
    private LocalDateTime dataDeInicio;
    private LocalDateTime dataDeTermino;
    private OrderStatus status;

    private UsuarioDTOResponse usuario;

    private List<MedicamentoHistoricoResumoDTOResponse> medicamentosHistoricos;

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

    public BigDecimal getDosagem() {
        return dosagem;
    }

    public void setDosagem(BigDecimal dosagem) {
        this.dosagem = dosagem;
    }

    public TipoUnidadeDeMedida getTipoUnidadeDeMedida() {
        return tipoUnidadeDeMedida;
    }

    public void setTipoUnidadeDeMedida(TipoUnidadeDeMedida tipoUnidadeDeMedida) {
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public UsuarioDTOResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTOResponse usuario) {
        this.usuario = usuario;
    }

    public List<MedicamentoHistoricoResumoDTOResponse> getMedicamentosHistoricos() {
        return medicamentosHistoricos;
    }

    public void setMedicamentosHistoricos(List<MedicamentoHistoricoResumoDTOResponse> medicamentosHistoricos) {
        this.medicamentosHistoricos = medicamentosHistoricos;
    }
}
