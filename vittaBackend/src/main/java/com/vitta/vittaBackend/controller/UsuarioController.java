package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.dto.request.usuario.UsuarioDTORequest;
import com.vitta.vittaBackend.dto.request.usuario.UsuarioAtualizarDTORequest;
import com.vitta.vittaBackend.dto.response.agenda.AgendaDoDiaDTOResponse;
import com.vitta.vittaBackend.dto.response.usuario.UsuarioDTOResponse;
import com.vitta.vittaBackend.service.AgendaService;
import com.vitta.vittaBackend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller para gerenciar as operações de Usuários e endpoints relacionados.
 * Expõe a API REST para cadastro, consulta, atualização e exclusão de usuários,
 * bem como para a consulta da agenda diária de um usuário específico.
 */
@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuário", description = "API para gerenciamento de usuários.")
public class UsuarioController {

    private UsuarioService usuarioService;
    private AgendaService agendaService;

    /**
     * Construtor para injeção de dependências dos serviços de Usuário и Agenda.
     * @param usuarioService Serviço com a lógica de negócio para Usuários.
     * @param agendaService Serviço com a lógica de negócio para a Agenda.
     */
    public UsuarioController(UsuarioService usuarioService, AgendaService agendaService) {
        this.usuarioService = usuarioService;
        this.agendaService = agendaService;
    }

    /**
     * Lista todos os usuários com status ATIVO.
     * @return ResponseEntity contendo uma lista de usuários e o status HTTP 200 OK.
     */
    @GetMapping("/listar")
    @Operation(summary = "Listar Usuários.", description = "Endpoint para listar todos os Usuários.")
    public ResponseEntity <List<UsuarioDTOResponse>> listarUsuarios() { return ResponseEntity.ok(usuarioService.listarUsuariosAtivos()); }

    /**
     * Busca um usuário ativo específico pelo seu ID.
     * @param usuarioId O ID do usuário a ser buscado.
     * @return ResponseEntity com o DTO do usuário encontrado e status 200 OK.
     * Retorna 404 Not Found se o usuário não existir ou estiver inativo.
     */
    @GetMapping("/listarUsuarioPorId/{usuarioId}")
    @Operation(summary = "Listar Usuário pelo ID dele.", description = "Endpoint para listar um Usuário, pelo ID.")
    public ResponseEntity<UsuarioDTOResponse> buscarUsuarioPorId(@PathVariable("usuarioId") Integer usuarioId) {
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.buscarUsuarioPorId(usuarioId);

        if (usuarioId == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usuarioDTOResponse);
        }
    }

    /**
     * Cadastra um novo usuário no sistema.
     * A senha será criptografada pelo serviço.
     * @param usuarioDTORequest DTO contendo os dados do novo usuário.
     * @return ResponseEntity com o DTO do usuário recém-criado e o status HTTP 201 Created.
     */
    @PostMapping("/cadastrar")
    @Operation(summary = "Criar novo Usuário.", description = "Endpoint para criar um novo registro de Usuário.")
    public ResponseEntity<UsuarioDTOResponse> cadastrarUsuario(@Valid @RequestBody UsuarioDTORequest usuarioDTORequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.cadastrarUsuario(usuarioDTORequest));
    }

    /**
     * Atualiza dados de um usuário existente (nome e telefone).
     * @param usuarioId O ID do usuário a ser atualizado.
     * @param usuarioAtualizarDTORequest DTO contendo os dados a serem alterados.
     * @return ResponseEntity com o DTO do usuário atualizado e o status HTTP 200 OK.
     */
    @PutMapping("/atualizar/{usuarioId}")
    @Operation(summary = "Atualizar todos os dados do Usuário.", description = "Endpoint para atualizar o registro do Usuário, pelo ID.")
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuarioPorId(
            @PathVariable("usuarioId") Integer usuarioId,
            @RequestBody @Valid UsuarioAtualizarDTORequest usuarioAtualizarDTORequest) {
        UsuarioDTOResponse usuarioAtualizado = usuarioService.atualizarUsuario(usuarioId, usuarioAtualizarDTORequest);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    /**
     * Realiza a exclusão lógica de um usuário, alterando seu status para INATIVO.
     * @param usuarioId O ID do usuário a ser desativado.
     * @return ResponseEntity com o status HTTP 204 No Content, indicando sucesso.
     */
    @DeleteMapping("/deletar/{usuarioId}")
    @Operation(summary = "Deletar todos os dados do Usuário.", description = "Endpoint para deletar o registro do Usuário, pelo ID.")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("usuarioId") Integer usuarioId) {
        usuarioService.deletarLogico(usuarioId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca a agenda de medicamentos do dia para um usuário específico.
     * @param usuarioId O ID do usuário para o qual a agenda será consultada.
     * @return ResponseEntity com a lista de agendamentos do dia e status 200 OK,
     * ou status 204 No Content se não houver agendamentos para o dia.
     */
    @GetMapping("/{usuarioId}/agendaDoDia")
    public ResponseEntity<List<AgendaDoDiaDTOResponse>> getAgendaDoDia(@PathVariable Integer usuarioId) {
        List<AgendaDoDiaDTOResponse> agenda = agendaService.getAgendaDoDia(usuarioId);
        if (agenda.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(agenda);
    }
}
