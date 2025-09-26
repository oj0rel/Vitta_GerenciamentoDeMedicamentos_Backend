package com.vitta.vittaBackend.dto.request.usuario;

import com.vitta.vittaBackend.enums.security.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para receber os dados necessários para o cadastro de um novo Usuário.
 * Esta classe representa o corpo da requisição (Request Body) para o endpoint de criação de usuário.
 */
public class UsuarioDTORequest {

    /**
     * Nome completo do usuário.
     * Não pode ser nulo ou vazio.
     */
    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    /**
     * Número de telefone do usuário para contato.
     * Não pode ser nulo ou vazio.
     */
    @NotBlank(message = "O telefone é obrigatório.")
    private String telefone;

    /**
     * Endereço de e-mail do usuário, que será usado para login.
     * Deve ser um formato de e-mail válido e não pode ser nulo ou vazio.
     */
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O formato do e-mail é inválido.")
    private String email;

    /**
     * Senha escolhida pelo usuário para acesso à conta.
     * É recomendado ter uma validação de tamanho mínimo para segurança.
     */
    @NotBlank(message = "A senha é obrigatória.")
    private String senha;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
