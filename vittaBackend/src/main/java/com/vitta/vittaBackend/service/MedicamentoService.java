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
     * Retorna uma lista de todos os medicamentos com status ATIVO.
     * @return Uma lista de {@link MedicamentoDTOResponse}.
     */
    public List<MedicamentoDTOResponse> listarMedicamentosAtivos() {
        // Alterado para chamar o seu método customizado
        return medicamentoRepository.listarMedicamentos()
                .stream()
                .map(MedicamentoDTOResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Busca um medicamento pelo seu ID.
     * @param medicamentoId O ID do medicamento a ser buscado.
     * @return O {@link MedicamentoDTOResponse} correspondente ao ID.
     */
    public MedicamentoDTOResponse buscarMedicamentoPorId(Integer medicamentoId) {
        Medicamento medicamento = this.validarMedicamento(medicamentoId);
        return new MedicamentoDTOResponse(medicamento);
    }

    /**
     * Cria um novo medicamento no catálogo.
     * @param medicamentoDTORequest O DTO contendo os dados para o novo medicamento.
     * @return O {@link MedicamentoDTOResponse} da entidade recém-criada.
     */
    @Transactional
    public MedicamentoDTOResponse cadastrarMedicamento(MedicamentoDTORequest medicamentoDTORequest) {

        Usuario usuario = usuarioRepository.findById(medicamentoDTORequest.getUsuarioId()).orElseThrow();

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
     * Atualiza os dados de um medicamento existente.
     * Apenas os campos não nulos no DTO serão atualizados.
     * @param medicamentoId O ID do medicamento a ser atualizado.
     * @param medicamentoAtualizarDTORequest O DTO com os novos dados.
     * @return O {@link MedicamentoDTOResponse} da entidade atualizada.
     * @throws EntityNotFoundException se o medicamento não for encontrado.
     */
    @Transactional
    public MedicamentoDTOResponse atualizarMedicamento(Integer medicamentoId, MedicamentoAtualizarDTORequest medicamentoAtualizarDTORequest) {
        Medicamento medicamentoExistente = validarMedicamento(medicamentoId);

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
     * Realiza a exclusão lógica de um medicamento, alterando seu status para INATIVO.
     * @param medicamentoId O ID do medicamento a ser desativado.
     * @throws EntityNotFoundException se o medicamento não for encontrado.
     */
    @Transactional
    public void deletarLogico(Integer medicamentoId) {
        validarMedicamento(medicamentoId);
        medicamentoRepository.apagadoLogicoMedicamento(medicamentoId);
    }

    /**
     * Retorna uma lista de todos os medicamentos com status INATIVO.
     * @return Uma lista de {@link MedicamentoDTOResponse}.
     */
    public List<MedicamentoDTOResponse> listarMedicamentosInativos() {
        return medicamentoRepository.listarMedicamentosInativos()
                .stream()
                .map(MedicamentoDTOResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Valida a existência de um medicamento pelo seu ID e o retorna.
     * Este é um método auxiliar privado para evitar a repetição de código nos
     * métodos públicos que precisam de buscar uma entidade antes de realizar uma ação.
     *
     * @param medicamentoId O ID do medicamento a ser validado e buscado.
     * @return A entidade {@link Medicamento} encontrada.
     */
    private Medicamento validarMedicamento(Integer medicamentoId) {
        Medicamento medicamento = medicamentoRepository.obterMedicamentoPeloId(medicamentoId);
        if (medicamento == null) {
            throw new EntityNotFoundException("Medicamento não encontrado com o ID: " + medicamentoId);
        }
        return medicamento;
    }
}
