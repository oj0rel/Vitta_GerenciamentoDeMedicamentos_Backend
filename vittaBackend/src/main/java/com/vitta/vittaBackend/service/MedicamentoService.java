package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.medicamento.MedicamentoDTORequest;
import com.vitta.vittaBackend.dto.request.medicamento.MedicamentoDTORequestAtualizar;
import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoDTOResponse;
import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.OrderStatus;
import com.vitta.vittaBackend.enums.medicamento.TipoUnidadeDeMedida;
import com.vitta.vittaBackend.repository.MedicamentoRepository;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicamentoService {
    
    private final MedicamentoRepository medicamentoRepository;
    private final UsuarioRepository usuarioRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    public MedicamentoService(MedicamentoRepository medicamentoRepository, UsuarioRepository usuarioRepository) {
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

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
        Medicamento medicamento = new Medicamento();

        // Preenche manualmente os campos que não precisam de conversão especial
        medicamento.setNome(medicamentoDTORequest.getNome());
        medicamento.setDosagem(medicamentoDTORequest.getDosagem());

        // converte o Integer que vem do DTORequest para o Enum TipoUnidadeDeMedida
        if (medicamentoDTORequest.getTipoUnidadeDeMedida() != null) {
            medicamento.setTipoUnidadeDeMedida(
                    TipoUnidadeDeMedida.fromCodigo(medicamentoDTORequest.getTipoUnidadeDeMedida())
            );
        }

        medicamento.setFrequencia(medicamentoDTORequest.getFrequencia());
        medicamento.setInstrucoes(medicamentoDTORequest.getInstrucoes());
        medicamento.setDataDeInicio(medicamentoDTORequest.getDataDeInicio());
        medicamento.setDataDeTermino(medicamentoDTORequest.getDataDeTermino());

        if (medicamentoDTORequest.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(medicamentoDTORequest.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Faz o vínculo bidirecional
            medicamento.setUsuario(usuario);
            //usuario.getMedicamentos().add(medicamento);

            // Salva explicitamente o medicamento
            Medicamento medicamentoSalvo = medicamentoRepository.save(medicamento);

            return modelMapper.map(medicamentoSalvo, MedicamentoDTOResponse.class);
        } else {
            Medicamento medicamentoSalvo = medicamentoRepository.save(medicamento);
            return modelMapper.map(medicamentoSalvo, MedicamentoDTOResponse.class);
        }
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
