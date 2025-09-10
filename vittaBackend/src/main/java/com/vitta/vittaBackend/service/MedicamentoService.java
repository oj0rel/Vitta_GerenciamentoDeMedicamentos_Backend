package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.medicamento.MedicamentoDTORequest;
import com.vitta.vittaBackend.dto.request.medicamento.MedicamentoDTORequestAtualizar;
import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoDTOResponse;
import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.enums.OrderStatus;
import com.vitta.vittaBackend.repository.MedicamentoRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicamentoService {
    
    private final MedicamentoRepository medicamentoRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    public MedicamentoService(MedicamentoRepository medicamentoRepository) { this.medicamentoRepository = medicamentoRepository; }

    //LISTAR MEDICAMENTOS
    public List<MedicamentoDTOResponse> listarMedicamentos() {
        return this.medicamentoRepository.listarMedicamentos()
                .stream()
                .map(medicamento -> modelMapper.map(medicamento, MedicamentoDTOResponse.class))
                .collect(Collectors.toList());
    }

    //LISTAR 1 MEDICAMENTO, PEGANDO PELO ID
    public MedicamentoDTOResponse buscarMedicamentoPorId(Integer medicamentoId) {
        Medicamento medicamento = medicamentoRepository.obterMedicamentoPeloId(medicamentoId);

        return modelMapper.map(medicamento, MedicamentoDTOResponse.class);
    }


    //CADASTRAR MEDICAMENTO
    @Transactional
    public MedicamentoDTOResponse cadastrarMedicamento(MedicamentoDTORequest medicamentoDTORequest) {
        Medicamento medicamento = modelMapper.map(medicamentoDTORequest, Medicamento.class);

        if (medicamento.getStatusTipo() == null) {
            medicamento.setStatusTipo(OrderStatus.ATIVO);
        }

        Medicamento medicamentoSave = this.medicamentoRepository.save(medicamento);
        MedicamentoDTOResponse medicamentoDTOResponse = modelMapper.map(medicamentoSave, MedicamentoDTOResponse.class);
        return medicamentoDTOResponse;
    }

    //ATUALIZAR 1 MEDICAMENTO, PEGANDO PELO ID
    @Transactional
    public MedicamentoDTOResponse atualizarMedicamentoPorId(Integer medicamentoId, MedicamentoDTORequestAtualizar medicamentoDTORequestAtualizar) {
        Medicamento medicamentoBuscado = this.validarMedicamento(medicamentoId);

        if (medicamentoBuscado != null) {
            modelMapper.map(medicamentoDTORequestAtualizar, medicamentoBuscado);
            Medicamento medicamentoRecebido = medicamentoRepository.save(medicamentoBuscado);
            return modelMapper.map(medicamentoRecebido, MedicamentoDTOResponse.class);
        } else {
            return null;
        }
    }

    //DELETAR 1 MEDICAMENTO, PEGANDO PELO ID
    @Transactional
    public void deletarMedicamento(Integer medicamentoId) { medicamentoRepository.apagadoLogicoMedicamento(medicamentoId); }


    //METODO PRIVADO PARA VALIDAR SE A IDENTIDADE EXISTE, PEGANDO PELO ID - para utilizar em outros métodos
    private Medicamento validarMedicamento(Integer medicamentoId) {
        Medicamento medicamento = medicamentoRepository.obterMedicamentoPeloId(medicamentoId);
        if (medicamento == null) {
            throw new RuntimeException("MEDICAMENTO não encontrado ou inativo.");
        }
        return medicamento;
    }

    //LISTAR MEDICAMENTOS INATIVOS
    public List<MedicamentoDTOResponse> listarMedicamentosInativos() {
        return this.medicamentoRepository.listarMedicamentosInativos()
                .stream()
                .map(medicamento -> modelMapper.map(medicamento, MedicamentoDTOResponse.class))
                .collect(Collectors.toList());
    }
}
