package com.vitta.vittaBackend.entity;

import com.vitta.vittaBackend.enums.OrderStatus;
import com.vitta.vittaBackend.enums.medicamento.TipoUnidadeDeMedida;
import jakarta.persistence.*;
import org.hibernate.query.Order;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

@Entity
@Table(name = "medicamento")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicamento_id")
    private Integer id;

    @Column(name = "medicamento_nome")
    private String nome;

    @Column(name = "medicamento_dosagem")
    private BigDecimal dosagem;

    @Column(name = "medicamento_tipo_unidade_de_medida")
    private Integer tipoUnidadeCodigo;

    @Transient
    private TipoUnidadeDeMedida tipoUnidadeDeMedida;

    @Column(name = "medicamento_frequencia")
    private Integer frequencia;

    @Column(name = "medicamento_instrucoes")
    private String instrucoes;

    @Column(name = "medicamento_data_de_inicio")
    private Date dataDeInicio;

    @Column(name = "medicamento_data_de_termino")
    private Date dataDeTermino;

    @Column(name = "medicamento_status")
    private Integer status;

    @Transient
    private OrderStatus statusTipo;

    @Column(name = "usuario_id")
    private Integer usuarioId;

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

    //FAZENDO GET E SET DE PARA GRAVAR O CÓDIGO DO ENUM - TIPO DE MEDICAMENTO
    public TipoUnidadeDeMedida getTipoUnidadeDeMedida() {
        return tipoUnidadeCodigo != null ?
                TipoUnidadeDeMedida.fromCodigo(tipoUnidadeCodigo) : null;
    }

    public void setTipoUnidadeDeMedida(TipoUnidadeDeMedida tipo) {
        this.tipoUnidadeCodigo = tipo != null ? tipo.getCodigo() : null;
        this.tipoUnidadeDeMedida = tipo;
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

    public Date getDataDeInicio() {
        return dataDeInicio;
    }

    public void setDataDeInicio(Date dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public Date getDataDeTermino() {
        return dataDeTermino;
    }

    public void setDataDeTermino(Date dataDeTermino) {
        this.dataDeTermino = dataDeTermino;
    }

    //FAZENDO GET E SET DE PARA GRAVAR O CÓDIGO DO ENUM NO BANCO - STATUS
    public OrderStatus getStatusTipo() {
        return status != null ? OrderStatus.fromCodigo(status) : null;
    }

    public void setStatusTipo(OrderStatus status) {
        this.status = status != null ? status.getCode() : null;
    }
}
