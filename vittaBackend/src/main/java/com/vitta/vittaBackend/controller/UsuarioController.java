package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.dto.request.usuario.UsuarioDTORequest;
import com.vitta.vittaBackend.dto.request.usuario.UsuarioAtualizarDTORequest;
import com.vitta.vittaBackend.dto.response.usuario.*;
import com.vitta.vittaBackend.dto.security.RecoveryJwtTokenDto;
import com.vitta.vittaBackend.dto.security.UsuarioLoginDto;
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
    @Operation(summary = "Listar Usuários", description = "Endpoint para listar todos os Usuários.")
    public ResponseEntity <List<UsuarioDTOResponse>> listarUsuarios() { return ResponseEntity.ok(usuarioService.listarUsuariosAtivos()); }

    /**
     * Cadastra um novo usuário no sistema.
     * <p>
     * Este endpoint recebe os dados de um novo usuário e os persiste no banco de dados.
     * </p>
     *
     * @param usuarioDTORequest DTO contendo os dados necessários para o cadastro do novo usuário.
     * @return um {@link ResponseEntity} vazio com o status HTTP 201 (CREATED) indicando
     * que o recurso foi criado com sucesso.
     */
    @PostMapping("/cadastrarNovoUsuario")
    @Operation(summary = "Cadastrar Usuário", description = "Endpoint para cadastrar Usuários, com SecurityJWT.")
    public ResponseEntity<Void> criarUsuario(@RequestBody UsuarioDTORequest usuarioDTORequest) {
        usuarioService.criarUsuario(usuarioDTORequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Autentica um usuário com base em suas credenciais e retorna um token JWT.
     * <p>
     * Este endpoint recebe um objeto com email e senha. Se as credenciais forem válidas,
     * o serviço de usuário gera e retorna um token de acesso.
     * </p>
     *
     * @param usuarioLoginDTO DTO contendo o email e a senha do usuário para autenticação.
     * @return um {@link ResponseEntity} contendo o {@link RecoveryJwtTokenDto} com o token gerado
     * e o status HTTP 200 (OK).
     */
    @PostMapping("/login")
    @Operation(summary = "Login de Usuário", description = "Endpoint para fazer o Login de 1 Usuário cadastrado no Banco.")
    public ResponseEntity<RecoveryJwtTokenDto> autenticarUsuarioSecurity(@RequestBody UsuarioLoginDto usuarioLoginDTO) {
        RecoveryJwtTokenDto token = usuarioService.autenticarUsuario(usuarioLoginDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * Endpoint de teste para verificar se um usuário está autenticado.
     * <p>
     * Apenas usuários com um token JWT válido podem acessar este endpoint.
     * </p>
     *
     * @return um {@link ResponseEntity} com uma mensagem de sucesso e o status HTTP 200 (OK).
     */
    @GetMapping("/test")
    @Operation(summary = "Teste de Usuário", description = "Endpoint para verificar se um Usuário está autenticado.")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    /**
     * Endpoint de teste para verificar se um usuário autenticado possui a role 'CUSTOMER'.
     * <p>
     * Este endpoint é protegido e requer que o usuário autenticado tenha uma permissão
     * específica de cliente para ser acessado.
     * </p>
     *
     * @return um {@link ResponseEntity} com uma mensagem de sucesso para clientes e o status HTTP 200 (OK).
     */
    @GetMapping("/test/customer")
    @Operation(summary = "Listar Role de Usuário", description = "Endpoint para verificar a Role do Usuário autenticado.")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    /**
     * Busca um usuário ativo específico pelo seu ID.
     * @param usuarioId O ID do usuário a ser buscado.
     * @return ResponseEntity com o DTO do usuário encontrado e status 200 OK.
     * Retorna 404 Not Found se o usuário não existir ou estiver inativo.
     */
    @GetMapping("/listarUsuarioPorId/{usuarioId}")
    @Operation(summary = "Listar Usuário pelo ID dele", description = "Endpoint para listar um Usuário, pelo ID.")
    public ResponseEntity<UsuarioDTOResponse> buscarUsuarioPorId(@PathVariable("usuarioId") Integer usuarioId) {
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.buscarUsuarioPorId(usuarioId);

        if (usuarioId == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usuarioDTOResponse);
        }
    }

    /**
     * Busca um usuário ativo específico, com seus agendamentos, pelo seu ID.
     * @param usuarioId O ID do usuário a ser buscado.
     * @return ResponseEntity com o DTO do usuário encontrado e status 200 OK.
     * Retorna 404 Not Found se o usuário não existir ou estiver inativo.
     */
    @GetMapping("/listarUsuarioAgendamentosPorId/{usuarioId}")
    @Operation(summary = "Listar Usuário e agendamentos pelo ID dele", description = "Endpoint para listar um Usuário com os agendamentos dele, pelo ID.")
    public ResponseEntity<UsuarioAgendamentosDTOResponse> buscarUsuarioAgendamentosPorId(@PathVariable("usuarioId") Integer usuarioId) {
        UsuarioAgendamentosDTOResponse usuarioAgendamentosDTOResponse = usuarioService.buscarUsuarioAgendamentosPorId(usuarioId);

        if (usuarioId == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usuarioAgendamentosDTOResponse);
        }
    }

    /**
     * Busca um usuário ativo específico, com seus históricos, pelo seu ID.
     * @param usuarioId O ID do usuário a ser buscado.
     * @return ResponseEntity com o DTO do usuário encontrado e status 200 OK.
     * Retorna 404 Not Found se o usuário não existir ou estiver inativo.
     */
    @GetMapping("/listarUsuarioHistoricoPorId/{usuarioId}")
    @Operation(summary = "Listar Usuário e históricos pelo ID dele", description = "Endpoint para listar um Usuário com os históricos dele, pelo ID.")
    public ResponseEntity<UsuarioHistoricoDTOResponse> buscarUsuarioHistoricoPorId(@PathVariable("usuarioId") Integer usuarioId) {
        UsuarioHistoricoDTOResponse usuarioHistoricoDTOResponse = usuarioService.buscarUsuarioHistoricosPorId(usuarioId);

        if (usuarioId == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usuarioHistoricoDTOResponse);
        }
    }

    /**
     * Busca um usuário ativo específico, com seus medicamentos, pelo seu ID.
     * @param usuarioId O ID do usuário a ser buscado.
     * @return ResponseEntity com o DTO do usuário encontrado e status 200 OK.
     * Retorna 404 Not Found se o usuário não existir ou estiver inativo.
     */
    @GetMapping("/listarUsuarioMedicamentosPorId/{usuarioId}")
    @Operation(summary = "Listar Usuário e medicamentos pelo ID dele", description = "Endpoint para listar um Usuário com os medicamentos dele, pelo ID.")
    public ResponseEntity<UsuarioMedicamentosDTOResponse> buscarUsuarioMedicamentosPorId(@PathVariable("usuarioId") Integer usuarioId) {
        UsuarioMedicamentosDTOResponse usuarioMedicamentosDTOResponse = usuarioService.buscarUsuarioMedicamentosPorId(usuarioId);

        if (usuarioId == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usuarioMedicamentosDTOResponse);
        }
    }

    /**
     * Busca um usuário ativo específico, com seus tratamentos, pelo seu ID.
     * @param usuarioId O ID do usuário a ser buscado.
     * @return ResponseEntity com o DTO do usuário encontrado e status 200 OK.
     * Retorna 404 Not Found se o usuário não existir ou estiver inativo.
     */
    @GetMapping("/listarUsuarioTratamentosPorId/{usuarioId}")
    @Operation(summary = "Listar Usuário e tratamentos pelo ID dele", description = "Endpoint para listar um Usuário com os tratamentos dele, pelo ID.")
    public ResponseEntity<UsuarioTratamentosDTOResponse> buscarUsuarioTratamentosPorId(@PathVariable("usuarioId") Integer usuarioId) {
        UsuarioTratamentosDTOResponse usuarioTratamentosDTOResponse = usuarioService.buscarUsuarioTratamentosPorId(usuarioId);

        if (usuarioId == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(usuarioTratamentosDTOResponse);
        }
    }

    /**
     * Atualiza dados de um usuário existente (nome e telefone).
     * @param usuarioId O ID do usuário a ser atualizado.
     * @param usuarioAtualizarDTORequest DTO contendo os dados a serem alterados.
     * @return ResponseEntity com o DTO do usuário atualizado e o status HTTP 200 OK.
     */
    @PutMapping("/atualizar/{usuarioId}")
    @Operation(summary = "Atualizar todos os dados do Usuário", description = "Endpoint para atualizar o registro do Usuário, pelo ID.")
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
    @Operation(summary = "Deletar todos os dados do Usuário", description = "Endpoint para deletar o registro do Usuário, pelo ID.")
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
//    @GetMapping("/{usuarioId}/agendaDoDia")
//    public ResponseEntity<List<AgendaDoDiaDTOResponse>> getAgendaDoDia(@PathVariable Integer usuarioId) {
//        List<AgendaDoDiaDTOResponse> agenda = agendaService.getAgendaDoDia(usuarioId);
//        if (agenda.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(agenda);
//    }
}
