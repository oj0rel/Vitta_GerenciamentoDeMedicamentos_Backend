package com.vitta.vittaBackend.entity;

import com.vitta.vittaBackend.enums.GeralStatus;
import com.vitta.vittaBackend.enums.medicamento.TipoUnidadeDeMedida;
import jakarta.persistence.*;

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

    @Column(name = "medicamento_principio_ativo")
    private String principioAtivo;

    @Column(name = "medicamento_laboratorio")
    private String laboratorio;

    @Column(name = "medicamento_tipo_unidade_de_medida")
    private TipoUnidadeDeMedida tipoUnidadeDeMedida; // Considere criar um Enum para isto

    @Column(name = "medicamento_status")
    private GeralStatus status = GeralStatus.ATIVO;

    @OneToMany(mappedBy = "medicamento")
    private List<Tratamento> tratamentos;

    /**
     * O usuário ao qual este medicamento pertence.
     * Relacionamento Muitos-para-Um com a entidade Usuario.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id")
    private Usuario usuario;

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

    public List<Tratamento> getTratamentos() {
        return tratamentos;
    }

    public void setTratamentos(List<Tratamento> tratamentos) {
        this.tratamentos = tratamentos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
