package com.vitta.vittaBackend.controller;

import com.vitta.vittaBackend.dto.request.usuario.UsuarioDTORequest;
import com.vitta.vittaBackend.dto.request.usuario.UsuarioAtualizarDTORequest;
import com.vitta.vittaBackend.dto.response.usuario.*;
import com.vitta.vittaBackend.dto.security.RecoveryJwtTokenDto;
import com.vitta.vittaBackend.dto.security.UsuarioLoginDto;
import com.vitta.vittaBackend.security.UserDetailsImpl;
import com.vitta.vittaBackend.service.AgendaService;
import com.vitta.vittaBackend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para gerenciar as operações de Usuário.
 * <p>
 * Expõe endpoints públicos para cadastro e login, e endpoints protegidos
 * para que o usuário autenticado possa gerenciar seus próprios dados.
 */
@RestController
@RequestMapping("/api/usuarios")
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
     * Cadastra um novo usuário no sistema.
     * <p>
     * Este endpoint recebe os dados de um novo usuário e os persiste no banco de dados.
     * </p>
     *
     * @param usuarioDTORequest DTO contendo os dados necessários para o cadastro do novo usuário.
     * @return Resposta com status 201 Created.
     */
    @PostMapping("/cadastrar")
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
     * @return O token JWT para ser usado nas próximas requisições.
     */
    @PostMapping("/login")
    @Operation(summary = "Login de Usuário", description = "Endpoint para fazer o Login de 1 Usuário cadastrado no Banco.")
    public ResponseEntity<RecoveryJwtTokenDto> autenticarUsuarioSecurity(@RequestBody UsuarioLoginDto usuarioLoginDTO) {
        RecoveryJwtTokenDto token = usuarioService.autenticarUsuario(usuarioLoginDTO);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * Busca um usuário ativo específico pelo seu ID.
     * @param userDetails Detalhes do usuário injetados pelo Spring Security.
     * @return O perfil completo do usuário logado.
     */
    @GetMapping("/buscarMeuPerfil")
    @Operation(summary = "Buscar meu perfil", description = "Endpoint para o usuário logado buscar seus próprios dados de perfil.")
    public ResponseEntity<UsuarioDTOResponse> buscarMeuPerfil(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Integer usuarioId = userDetails.getUserId();
        UsuarioDTOResponse usuarioDTOResponse = usuarioService.buscarMeuPerfil(usuarioId);

        return ResponseEntity.ok(usuarioDTOResponse);
    }

    /**
     * Atualiza dados de um usuário existente (nome e telefone).
     * @param usuarioAtualizarDTORequest DTO contendo os dados a serem alterados.
     * @param userDetails Detalhes do usuário injetados pelo Spring Security.
     * @return O perfil do usuário com os dados atualizados.
     */
    @PutMapping("/atualizarMeuPerfil")
    @Operation(summary = "Atualizar meu perfil", description = "Endpoint para o usuário logado atualizar seus dados de perfil.")
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuarioPorId(
            @RequestBody @Valid UsuarioAtualizarDTORequest usuarioAtualizarDTORequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Integer usuarioId = userDetails.getUserId();
        UsuarioDTOResponse usuarioAtualizado = usuarioService.atualizarMeuPerfil(usuarioId, usuarioAtualizarDTORequest);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    /**
     * Realiza a exclusão lógica de um usuário, alterando seu status para INATIVO.
     * @param userDetails Detalhes do usuário injetados pelo Spring Security.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping("/deletarMinhaConta")
    @Operation(summary = "Deletar minha conta", description = "Endpoint para o usuário logado deletar (logicamente) sua própria conta.")
    public ResponseEntity<Void> deletarUsuario(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Integer usuarioId = userDetails.getUserId();
        usuarioService.deletarMinhaConta(usuarioId);
        return ResponseEntity.noContent().build();
    }



    /**
     * Busca os agendamentos do usuário logado.
     * @param userDetails Detalhes do usuário injetados pelo Spring Security.
     */
    @GetMapping("/listarMeusAgendamentos")
    @Operation(summary = "Listar meus agendamentos", description = "Busca o usuário logado com sua lista de agendamentos cadastrados.")
    public ResponseEntity<UsuarioAgendamentosDTOResponse> buscarMeusAgendamentos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Integer usuarioId = userDetails.getUserId();
        return ResponseEntity.ok(usuarioService.buscarMeusAgendamentos(usuarioId));
    }

    /**
     * Busca os históricos do usuário logado.
     * @param userDetails Detalhes do usuário injetados pelo Spring Security.
     */
    @GetMapping("/listarMeusHistoricos")
    @Operation(summary = "Listar meus históricos", description = "Busca o usuário logado com seu histórico de uso de medicamentos cadastrados.")
    public ResponseEntity<UsuarioHistoricoDTOResponse> buscarMeusHistoricos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Integer usuarioId = userDetails.getUserId();
        return ResponseEntity.ok(usuarioService.buscarMeusHistoricos(usuarioId));
    }

    /**
     * Busca os medicamentos do usuário logado.
     * @param userDetails Detalhes do usuário injetados pelo Spring Security.
     */
    @GetMapping("/listarMeusMedicamentos")
    @Operation(summary = "Listar meus medicamentos", description = "Busca o usuário logado com sua lista de medicamentos cadastrados.")
    public ResponseEntity<UsuarioMedicamentosDTOResponse> buscarMeusMedicamentos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Integer usuarioId = userDetails.getUserId();
        return ResponseEntity.ok(usuarioService.buscarMeusMedicamentos(usuarioId));
    }

    /**
     * Busca os tratamentos do usuário logado.
     * @param userDetails Detalhes do usuário injetados pelo Spring Security.
     */
    @GetMapping("/listarMeusTratamentos")
    @Operation(summary = "Listar meus tratamentos", description = "Busca o usuário logado com sua lista de tratamentos cadastrados.")
    public ResponseEntity<UsuarioTratamentosDTOResponse> buscarMeusTratamentos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Integer usuarioId = userDetails.getUserId();
        return ResponseEntity.ok(usuarioService.buscarMeusTratamentos(usuarioId));
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

    //    /**
//     * Lista todos os usuários com status ATIVO.
//     * @return ResponseEntity contendo uma lista de usuários e o status HTTP 200 OK.
//     */
//    @GetMapping("/listar")
//    @Operation(summary = "Listar Usuários", description = "Endpoint para listar todos os Usuários.")
//    public ResponseEntity <List<UsuarioDTOResponse>> listarUsuarios() { return ResponseEntity.ok(usuarioService.listarUsuariosAtivos()); }


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
