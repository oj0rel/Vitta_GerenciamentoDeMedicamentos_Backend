package com.vitta.vittaBackend.entity;

import com.vitta.vittaBackend.enums.TipoFrequencia;
import com.vitta.vittaBackend.enums.TratamentoStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tratamento")
public class Tratamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tratamento_id")
    private Integer id;

    @Column(name = "tratamento_dosagem")
    private BigDecimal dosagem;

    @Column(name = "tratamento_instrucoes")
    private String instrucoes;

    @Column(name = "tratamento_data_de_inicio")
    private LocalDate dataDeInicio;

    @Column(name = "tratamento_data_de_termino")
    private LocalDate dataDeTermino;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tratamento_tipo_de_frequencia")
    private TipoFrequencia tipoDeFrequencia;

    @Column(name = "tratamento_intervalo_em_horas")
    private Integer intervaloEmHoras;

    @Column(name = "tratamento_horarios_especificos")
    private String horariosEspecificos;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tratamento_status")
    private TratamentoStatus status = TratamentoStatus.ATIVO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicamento_id")
    private Medicamento medicamento;

    @OneToMany(mappedBy = "tratamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Agendamento> agendamentos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public TipoFrequencia getTipoDeFrequencia() {
        return tipoDeFrequencia;
    }

    public void setTipoDeFrequencia(TipoFrequencia tipoDeFrequencia) {
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

    public TratamentoStatus getStatus() {
        return status;
    }

    public void setStatus(TratamentoStatus status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public List<Agendamento> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<Agendamento> agendamentos) {
        this.agendamentos = agendamentos;
    }
}
