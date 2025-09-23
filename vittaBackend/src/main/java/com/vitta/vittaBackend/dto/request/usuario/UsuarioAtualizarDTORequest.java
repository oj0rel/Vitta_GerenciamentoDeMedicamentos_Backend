package com.vitta.vittaBackend.dto.request.usuario;

/**
 * DTO para receber os dados na atualização de um Usuário existente.
 * Todos os campos são opcionais, permitindo a atualização parcial do perfil do usuário.
 * Campos sensíveis como e-mail e senha são intencionalmente omitidos.
 */
public class UsuarioAtualizarDTORequest {

    /**
     * O novo nome completo do usuário.
     * Se fornecido, não deve exceder 45 caracteres.
     */
    private String nome;

    /**
     * O novo número de telefone do usuário.
     * Se fornecido, não deve exceder 20 caracteres.
     */
    private String telefone;

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

}
