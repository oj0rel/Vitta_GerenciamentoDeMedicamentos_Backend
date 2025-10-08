package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.medicamento.MedicamentoDTORequest;
import com.vitta.vittaBackend.dto.request.medicamento.MedicamentoAtualizarDTORequest;
import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoDTOResponse;
import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.medicamento.TipoUnidadeDeMedida;
import com.vitta.vittaBackend.repository.MedicamentoRepository;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de serviço para gerenciar a lógica de negócio do catálogo de medicamentos.
 */
@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public MedicamentoService(
            MedicamentoRepository medicamentoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Retorna uma lista de todos os medicamentos ativos de um usuário específico.
     * @param usuarioId O ID do usuário autenticado.
     * @return Uma lista de {@link MedicamentoDTOResponse}.
     */
    public List<MedicamentoDTOResponse> listarMedicamentosPorUsuario(Integer usuarioId) {
        // Alterado para chamar o seu método customizado
        return medicamentoRepository.listarMedicamentos(usuarioId)
                .stream()
                .map(MedicamentoDTOResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um medicamento pelo seu ID, garantindo que pertença ao usuário.
     * @param medicamentoId O ID do medicamento a ser buscado.
     * @param usuarioId O ID do usuário autenticado.
     * @return O {@link MedicamentoDTOResponse} correspondente ao ID.
     */
    public MedicamentoDTOResponse listarMedicamentoPorId(Integer medicamentoId, Integer usuarioId) {
        Medicamento medicamento = validarMedicamento(medicamentoId, usuarioId);
        return new MedicamentoDTOResponse(medicamento);
    }

    /**
     * Cria um novo medicamento no catálogo do usuário autenticado.
     * @param medicamentoDTORequest O DTO contendo os dados para o novo medicamento.
     * @param usuarioId O ID do usuário autenticado (vindo do token).
     * @return O {@link MedicamentoDTOResponse} da entidade recém-criada.
     */
    @Transactional
    public MedicamentoDTOResponse cadastrarMedicamento(MedicamentoDTORequest medicamentoDTORequest, Integer usuarioId) {

        Usuario usuario = usuarioRepository.getReferenceById(usuarioId);

        Medicamento novoMedicamento = new Medicamento();

        novoMedicamento.setNome(medicamentoDTORequest.getNome());
        novoMedicamento.setPrincipioAtivo(medicamentoDTORequest.getPrincipioAtivo());
        novoMedicamento.setLaboratorio(medicamentoDTORequest.getLaboratorio());

        if (medicamentoDTORequest.getTipoUnidadeDeMedida() != null) {
            novoMedicamento.setTipoUnidadeDeMedida(
                    TipoUnidadeDeMedida.fromCodigo(medicamentoDTORequest.getTipoUnidadeDeMedida())
            );
        }

        novoMedicamento.setUsuario(usuario);

        Medicamento medicamentoSalvo = medicamentoRepository.save(novoMedicamento);

        return new MedicamentoDTOResponse(medicamentoSalvo);
    }

    /**
     * Atualiza os dados de um medicamento existente, garantindo que pertença ao usuário.
     * @param medicamentoId O ID do medicamento a ser atualizado.
     * @param usuarioId O ID do usuário autenticado.
     * @param medicamentoAtualizarDTORequest O DTO com os novos dados.
     * @return O {@link MedicamentoDTOResponse} da entidade atualizada.
     */
    @Transactional
    public MedicamentoDTOResponse atualizarMedicamento(Integer medicamentoId, Integer usuarioId, MedicamentoAtualizarDTORequest medicamentoAtualizarDTORequest) {
        Medicamento medicamentoExistente = validarMedicamento(medicamentoId, usuarioId);

        if (medicamentoAtualizarDTORequest.getNome() != null) {
            medicamentoExistente.setNome(medicamentoAtualizarDTORequest.getNome());
        }
        if (medicamentoAtualizarDTORequest.getPrincipioAtivo() != null) {
            medicamentoExistente.setPrincipioAtivo(medicamentoAtualizarDTORequest.getPrincipioAtivo());
        }
        if (medicamentoAtualizarDTORequest.getLaboratorio() != null) {
            medicamentoExistente.setLaboratorio(medicamentoAtualizarDTORequest.getLaboratorio());
        }
        if (medicamentoAtualizarDTORequest.getTipoUnidadeDeMedida() != null) {
            medicamentoExistente.setTipoUnidadeDeMedida(
                    TipoUnidadeDeMedida.fromCodigo(medicamentoAtualizarDTORequest.getTipoUnidadeDeMedida())
            );
        }

        Medicamento medicamentoAtualizado = medicamentoRepository.save(medicamentoExistente);

        return new MedicamentoDTOResponse(medicamentoAtualizado);
    }

    /**
     * Realiza a exclusão lógica de um medicamento, garantindo que pertença ao usuário.
     * @param medicamentoId O ID do medicamento a ser desativado.
     * @param usuarioId O ID do usuário autenticado.
     */
    @Transactional
    public void deletarLogico(Integer medicamentoId, Integer usuarioId) {
        validarMedicamento(medicamentoId, usuarioId);
        medicamentoRepository.apagarLogicoMedicamento(medicamentoId, usuarioId);
    }

    /**
     * Retorna uma lista de todos os medicamentos inativos de um usuário específico.
     * @param usuarioId O ID do usuário autenticado.
     * @return Uma lista de {@link MedicamentoDTOResponse}.
     */
    public List<MedicamentoDTOResponse> listarMedicamentosInativos(Integer usuarioId) {
        return medicamentoRepository.listarMedicamentosInativos(usuarioId)
                .stream()
                .map(MedicamentoDTOResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Valida a existência de um medicamento pelo seu ID e se ele pertence ao usuário.
     * @param medicamentoId O ID do medicamento a ser validado.
     * @param usuarioId O ID do usuário autenticado.
     * @return A entidade {@link Medicamento} encontrada.
     * @throws EntityNotFoundException se o medicamento não for encontrado ou não pertencer ao usuário.
     */
    private Medicamento validarMedicamento(Integer medicamentoId, Integer usuarioId) {
        Medicamento medicamento = medicamentoRepository.listarMedicamentoPorId(medicamentoId, usuarioId);
        if (medicamento == null) {
            throw new EntityNotFoundException("Medicamento não encontrado com o ID: " + medicamentoId);
        }
        return medicamento;
    }
}
