package com.vitta.vittaBackend.entity;

import com.vitta.vittaBackend.enums.UsuarioStatus;
import jakarta.persistence.*;

import java.util.List;

/**
 * Representa um utilizador da aplicação Vitta no banco de dados.
 * Esta entidade armazena as informações de login e os dados pessoais do utilizador.
 */
@Entity
@Table(name = "usuario")
public class Usuario {

    /**
     * Identificador único do utilizador, gerado automaticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;

    /**
     * Nome completo do utilizador.
     */
    @Column(name = "usuario_nome")
    private String nome;

    /**
     * Número de telefone do utilizador, pode ser usado para contato ou notificações.
     */
    @Column(name = "usuario_telefone")
    private String telefone;

    /**
     * Endereço de e-mail do utilizador, usado para login e comunicação.
     * Este campo deve ser único para cada utilizador.
     */
    @Column(name = "usuario_email", unique = true)
    private String email;

    /**
     * Senha do utilizador.
     * Nota de segurança: esta senha deve ser armazenada no banco de dados
     * de forma criptografada (hash), nunca em texto plano.
     */
    @Column(name = "usuario_senha")
    private String senha;

    /**
     * Status atual da conta do utilizador (ex: ATIVO, INATIVO, BLOQUEADO).
     * O valor padrão para um novo utilizador é ATIVO.
     */
    @Column(name = "usuario_status")
    private UsuarioStatus status = UsuarioStatus.ATIVO;

    /**
     * Lista de todos os tratamentos que pertencem a este utilizador.
     * Este é o lado "um" da relação um-para-muitos com a entidade Tratamento.
     */
    @OneToMany(mappedBy = "usuario")
    private List<Tratamento> tratamentos;

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsuarioStatus getStatus() {
        return status;
    }

    public void setStatus(UsuarioStatus status) {
        this.status = status;
    }

    public List<Tratamento> getTratamentos() {
        return tratamentos;
    }

    public void setTratamentos(List<Tratamento> tratamentos) {
        this.tratamentos = tratamentos;
    }
}
