package com.vitta.vittaBackend.dto.response.usuario;

import com.vitta.vittaBackend.dto.response.medicamentoHistorico.MedicamentoHistoricoDTOResponse;
import com.vitta.vittaBackend.dto.response.tratamento.TratamentoResumoDTOResponse;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.UsuarioStatus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO para representar a resposta completa dos dados de um Usuário,
 * incluindo uma lista resumida de seus históricos.
 */
public class UsuarioHistoricoDTOResponse {

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
     * Lista resumida dos históricos associados a este usuário.
     */
    private List<MedicamentoHistoricoDTOResponse> historicos;

    /**
     * Construtor vazio.
     */
    public UsuarioHistoricoDTOResponse() {
    }

    /**
     * Construtor de mapeamento a partir da entidade Usuário.
     * Facilita a conversão da entidade {@link com.vitta.vittaBackend.entity.Usuario} e suas relações para este DTO.
     * @param usuarioEntity A entidade vinda do banco de dados.
     */
    public UsuarioHistoricoDTOResponse(Usuario usuarioEntity) {
        this.id = usuarioEntity.getId();
        this.nome = usuarioEntity.getNome();
        this.telefone = usuarioEntity.getTelefone();
        this.email = usuarioEntity.getEmail();
        this.status = usuarioEntity.getStatus();

        //converte a lista de entidades Tratamento para uma lista de DTOs de resumo
        if (usuarioEntity.getHistoricos() != null) {
            this.historicos = usuarioEntity.getHistoricos().stream()
                    .map(MedicamentoHistoricoDTOResponse::new)
                    .collect(Collectors.toList());
        }
    }
}
