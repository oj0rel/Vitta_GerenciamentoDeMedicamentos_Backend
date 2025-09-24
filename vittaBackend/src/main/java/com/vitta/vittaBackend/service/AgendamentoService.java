package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.agendamento.AgendamentoAtualizarDTORequest;
import com.vitta.vittaBackend.dto.request.agendamento.AgendamentoDTORequest;
import com.vitta.vittaBackend.dto.response.agendamento.AgendamentoDTOResponse;
import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.entity.Tratamento;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.agendamento.AgendamentoStatus;
import com.vitta.vittaBackend.enums.agendamento.TipoDeAlerta;
import com.vitta.vittaBackend.repository.AgendamentoRepository;
import com.vitta.vittaBackend.repository.MedicamentoHistoricoRepository;
import com.vitta.vittaBackend.repository.TratamentoRepository;
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
    private final MedicamentoHistoricoRepository medicamentoHistoricoRepository;
    private final UsuarioRepository usuarioRepository;
    private final TratamentoRepository tratamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AgendamentoService(
            AgendamentoRepository agendamentoRepository,
            MedicamentoHistoricoRepository medicamentoHistoricoRepository,
            UsuarioRepository usuarioRepository,
            TratamentoRepository tratamentoRepository
    ) {
        this.agendamentoRepository = agendamentoRepository;
        this.medicamentoHistoricoRepository = medicamentoHistoricoRepository;
        this.usuarioRepository = usuarioRepository;
        this.tratamentoRepository = tratamentoRepository;
    }

    //LISTAR OS AGENDAMENTOS
    public List<AgendamentoDTOResponse> listarAgendamentos() {
        List<Agendamento> todosAgendamentos = agendamentoRepository.listarAgendamentos();

        return agendamentoRepository.listarAgendamentos()
                .stream()
                .map(AgendamentoDTOResponse::new)
                .collect(Collectors.toList());
    }

    //LISTAR 1 AGENDAMENTO, PEGANDO PELO ID
    public AgendamentoDTOResponse buscarAgendamentoPorId(Integer agendamentoId) {
        Agendamento agendamento = this.validarAgendamento(agendamentoId);
        return new AgendamentoDTOResponse(agendamento);
    }

    //CADASTRAR AGENDAMENTO
    @Transactional
    public AgendamentoDTOResponse cadastrarAgendamento(AgendamentoDTORequest agendamentoDTORequest) {
        Usuario usuario = usuarioRepository.findById(agendamentoDTORequest.getUsuarioId()).orElseThrow();
        Tratamento tratamento = tratamentoRepository.findById(agendamentoDTORequest.getTratamentoId()).orElseThrow();

        Agendamento agendamento = new Agendamento();

        agendamento.setHorarioDoAgendamento(agendamentoDTORequest.getHorarioDoAgendamento());

        if (agendamentoDTORequest.getTipoDeAlerta() != null) {
            agendamento.setTipoDeAlerta(
                    TipoDeAlerta.fromCodigo(agendamentoDTORequest.getTipoDeAlerta())
            );
        }

        agendamento.setTratamento(tratamento);
        agendamento.setUsuario(usuario);

        Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
        return new AgendamentoDTOResponse(agendamentoSalvo);
    }

    //ATUALIZAR 1 AGENDAMENTO, PEGANDO PELO ID
    @Transactional
    public AgendamentoDTOResponse atualizarAgendamento(Integer agendamentoId, AgendamentoAtualizarDTORequest agendamentoAtualizarDTORequest) {
        Agendamento agendamentoBuscado = this.validarAgendamento(agendamentoId);

        if (agendamentoBuscado != null) {

            agendamentoBuscado.setHorarioDoAgendamento(agendamentoAtualizarDTORequest.getHorarioDoAgendamento());

            if (agendamentoAtualizarDTORequest.getTipoDeAlerta() != null) {
                agendamentoBuscado.setTipoDeAlerta(
                        TipoDeAlerta.fromCodigo(agendamentoAtualizarDTORequest.getTipoDeAlerta())
                );
            }

            // converte o Integer que vem do AtualizarDTORequest para o Enum AgendamentoStatus
            if (agendamentoAtualizarDTORequest.getStatus() != null) {
                agendamentoBuscado.setStatus(
                        AgendamentoStatus.fromCodigo(agendamentoAtualizarDTORequest.getStatus())
                );
            }

            Agendamento agendamentoRecebido = agendamentoRepository.save(agendamentoBuscado);
            return new AgendamentoDTOResponse(agendamentoRecebido);
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
