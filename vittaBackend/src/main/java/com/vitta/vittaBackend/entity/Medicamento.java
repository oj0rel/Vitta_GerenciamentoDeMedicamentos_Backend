package com.vitta.vittaBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vitta.vittaBackend.enums.GeralStatus;
import com.vitta.vittaBackend.enums.medicamento.TipoUnidadeDeMedida;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private TipoUnidadeDeMedida tipoUnidadeDeMedida;

    @Column(name = "medicamento_frequencia")
    private Integer frequenciaPorDia;

    @Column(name = "medicamento_instrucoes")
    private String instrucoes;

    @Column(name = "medicamento_data_de_inicio")
    private LocalDateTime dataDeInicio;

    @Column(name = "medicamento_data_de_termino")
    private LocalDateTime dataDeTermino;

    @Column(name = "medicamento_status")
    private GeralStatus status = GeralStatus.ATIVO;


    //isso aki é para enviar a tabela Medicamento para Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    //para trazer a tabela MedicamentoHistorico para Medicamento
    @OneToMany(mappedBy = "medicamento")
    @JsonIgnore
    private List<MedicamentoHistorico> medicamentosHistoricos;

    //para trazer a tabela Agendamento para Medicamento
    @OneToMany(mappedBy = "medicamento")
    @JsonIgnore
    private List<Agendamento> agendamentos;

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

    public Integer getFrequenciaPorDia() {
        return frequenciaPorDia;
    }

    public void setFrequenciaPorDia(Integer frequenciaPorDia) {
        this.frequenciaPorDia = frequenciaPorDia;
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

    public GeralStatus getStatus() {
        return status;
    }

    public void setStatus(GeralStatus status) {
        this.status = status;
    }

    //GET E SET - TABELA ESTRANGEIRA
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<MedicamentoHistorico> getMedicamentosHistoricos() {
        return medicamentosHistoricos;
    }

    public void setMedicamentosHistoricos(List<MedicamentoHistorico> medicamentosHistoricos) {
        this.medicamentosHistoricos = medicamentosHistoricos;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }
}
