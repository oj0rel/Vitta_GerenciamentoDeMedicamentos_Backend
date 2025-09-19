package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.agendamento.AgendamentoAtualizarDTORequest;
import com.vitta.vittaBackend.dto.request.agendamento.AgendamentoDTORequest;
import com.vitta.vittaBackend.dto.response.agendamento.AgendamentoDTOResponse;
import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoResumoDTOResponse;
import com.vitta.vittaBackend.dto.response.usuario.UsuarioResumoDTOResponse;
import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.agendamento.AgendamentoStatus;
import com.vitta.vittaBackend.enums.agendamento.TipoDeAlerta;
import com.vitta.vittaBackend.repository.AgendamentoRepository;
import com.vitta.vittaBackend.repository.MedicamentoRepository;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AgendamentoService(AgendamentoRepository agendamentoRepository,
                              MedicamentoRepository medicamentoRepository,
                              UsuarioRepository usuarioRepository) {

        this.agendamentoRepository = agendamentoRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    //LISTAR OS AGENDAMENTOS
    public List<AgendamentoDTOResponse> listarAgendamentos() {
        return this.agendamentoRepository.listarAgendamentos()
                .stream()
                .map(agendamento -> modelMapper.map(agendamento, AgendamentoDTOResponse.class))
                .collect(Collectors.toList());
    }

    //LISTAR 1 AGENDAMENTO, PEGANDO PELO ID
    public AgendamentoDTOResponse buscarAgendamentoPorId(Integer agendamentoId) {
        Agendamento agendamento = agendamentoRepository.obterAgendamentoPeloId(agendamentoId);

        return modelMapper.map(agendamento, AgendamentoDTOResponse.class);
    }

    //CADASTRAR AGENDAMENTO
    @Transactional
    public AgendamentoDTOResponse cadastrarAgendamento(AgendamentoDTORequest agendamentoDTORequest) {
        Agendamento agendamento = new Agendamento();

        agendamento.setHorarioDoAgendamento(agendamentoDTORequest.getHorarioDoAgendamento());

        // converte o Integer que vem do DTORequest para o Enum TipoDeAlerta
        if (agendamentoDTORequest.getTipoDeAlerta() != null) {
            agendamento.setTipoDeAlerta(
                    TipoDeAlerta.fromCodigo(agendamentoDTORequest.getTipoDeAlerta())
            );
        }

        if (agendamentoDTORequest.getMedicamentoId() != null )  {
            Medicamento medicamento = medicamentoRepository.findById(agendamentoDTORequest.getMedicamentoId())
                    .orElseThrow(() -> new RuntimeException("Medicamento não encontrado"));
            agendamento.setMedicamento(medicamento);
        }
        if (agendamentoDTORequest.getUsuarioId() != null )  {
            Usuario usuario = usuarioRepository.findById(agendamentoDTORequest.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            agendamento.setUsuario(usuario);
        }

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);

        AgendamentoDTOResponse responseDTO = new AgendamentoDTOResponse();

        responseDTO.setId(agendamentoSalvo.getId());
        responseDTO.setHorarioDoAgendamento(agendamentoSalvo.getHorarioDoAgendamento());
        responseDTO.setStatus(agendamentoSalvo.getStatus());
        responseDTO.setTipoDeAlerta(agendamentoSalvo.getTipoDeAlerta());

        if (agendamentoSalvo.getUsuario() != null) {
            responseDTO.setUsuario(new UsuarioResumoDTOResponse(agendamentoSalvo.getUsuario()));
        }
        if (agendamentoSalvo.getMedicamento() != null) {
            MedicamentoResumoDTOResponse medDTO = new MedicamentoResumoDTOResponse(agendamentoSalvo.getMedicamento());
            responseDTO.setMedicamento(medDTO);
        }

        return responseDTO;
    }

    //ATUALIZAR 1 AGENDAMENTO, PEGANDO PELO ID
    @Transactional
    public AgendamentoDTOResponse atualizarAgendamento(Integer agendamentoId, AgendamentoAtualizarDTORequest agendamentoAtualizarDTORequest) {
        Agendamento agendamentoBuscado = this.validarAgendamento(agendamentoId);

        if (agendamentoBuscado != null) {

            // converte o Integer que vem do AtualizarDTORequest para o Enum AgendamentoStatus
            if (agendamentoAtualizarDTORequest.getStatus() != null) {
                agendamentoBuscado.setStatus(
                        AgendamentoStatus.fromCodigo(agendamentoAtualizarDTORequest.getStatus())
                );
            }

            modelMapper.map(agendamentoAtualizarDTORequest, agendamentoBuscado);

            Agendamento agendamentoRecebido = agendamentoRepository.save(agendamentoBuscado);
            return modelMapper.map(agendamentoRecebido, AgendamentoDTOResponse.class);
        } else {
            return null;
        }
    }

    //DELETAR 1 AGENDAMENTO, PEGANDO PELO ID
    @Transactional
    public void deletarAgendamento(Integer agendamentoId) { agendamentoRepository.apagadoLogicoAgendamento(agendamentoId); }


    //METODO PRIVADO PARA VALIDAR SE O AGENDAMENTO, PEGANDO PELO ID - para utilizar em outros métodos
    private Agendamento validarAgendamento(Integer agendamentoId) {
        Agendamento agendamento = agendamentoRepository.obterAgendamentoPeloId(agendamentoId);
        if (agendamento == null) {
            throw new RuntimeException("Agendamento não encontrado ou inativo.");
        }
        return agendamento;
    }
}
