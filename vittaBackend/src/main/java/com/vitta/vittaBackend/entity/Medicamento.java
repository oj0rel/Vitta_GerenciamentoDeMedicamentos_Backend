package com.vitta.vittaBackend.entity;

import com.vitta.vittaBackend.enums.medicamento.TipoUnidadeDeDosagem;
import jakarta.persistence.*;

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
    private String dosagem;

    @Column(name = "medicamento_tipo_unidade_de_dosagem")
    private TipoUnidadeDeDosagem tipoUnidadeDeDosagem;

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
}
