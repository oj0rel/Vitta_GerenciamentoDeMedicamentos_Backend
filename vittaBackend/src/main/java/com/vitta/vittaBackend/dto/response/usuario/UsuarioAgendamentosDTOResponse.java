package com.vitta.vittaBackend.dto.response.usuario;

import com.vitta.vittaBackend.dto.response.agendamento.AgendamentoResumoDTOResponse;
import com.vitta.vittaBackend.dto.response.tratamento.TratamentoResumoDTOResponse;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.UsuarioStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO para representar a resposta completa dos dados de um Usuário,
 * incluindo uma lista resumida de seus agendamentos.
 */
public class UsuarioAgendamentosDTOResponse {

    /**
     * O ID único do usuário no banco de dados.
     */
    private Integer id;

    /**
     * O nome completo do usuário.
     */
    private String nome;

    /**
     * O número de telefone do usuário.
     */
    private String telefone;

    /**
     * O endereço de e-mail do usuário.
     */
    private String email;

    /**
     * O status atual da conta do usuário (ex: ATIVO, INATIVO).
     */
    private UsuarioStatus status;

    /**
     * Lista resumida dos agendamentos associados a este usuário.
     */
    private List<AgendamentoResumoDTOResponse> agendamentos;

    /**
     * Construtor vazio.
     */
    public UsuarioAgendamentosDTOResponse() {
    }

    /**
     * Construtor de mapeamento a partir da entidade Usuário.
     * Facilita a conversão da entidade {@link com.vitta.vittaBackend.entity.Usuario} e suas relações para este DTO.
     * @param usuarioEntity A entidade vinda do banco de dados.
     */
    public UsuarioAgendamentosDTOResponse(Usuario usuarioEntity) {
        this.id = usuarioEntity.getId();
        this.nome = usuarioEntity.getNome();
        this.telefone = usuarioEntity.getTelefone();
        this.email = usuarioEntity.getEmail();
        this.status = usuarioEntity.getStatus();

        //converte a lista de entidades Tratamento para uma lista de DTOs de resumo
        if (usuarioEntity.getAgendamentos() != null) {
            this.agendamentos = usuarioEntity.getAgendamentos().stream()
                    .map(AgendamentoResumoDTOResponse::new)
                    .collect(Collectors.toList());
        }
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UsuarioStatus getStatus() {
        return status;
    }

    public void setStatus(UsuarioStatus status) {
        this.status = status;
    }

    public List<AgendamentoResumoDTOResponse> getAgendamentos() {
        return agendamentos;
    }

    public void setAgendamentos(List<AgendamentoResumoDTOResponse> agendamentos) {
        this.agendamentos = agendamentos;
    }
}
