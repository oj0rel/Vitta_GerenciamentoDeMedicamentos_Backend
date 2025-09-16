package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.agendamento.AgendamentoDTORequest;
import com.vitta.vittaBackend.dto.request.medicamentoHistorico.MedicamentoHistoricoDTORequest;
import com.vitta.vittaBackend.dto.response.agendamento.AgendamentoDTOResponse;
import com.vitta.vittaBackend.dto.response.medicamentoHistorico.MedicamentoHistoricoDTOResponse;
import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.entity.MedicamentoHistorico;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.agendamento.TipoDeAlerta;
import com.vitta.vittaBackend.enums.medicamento.TipoUnidadeDeMedida;
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
        return modelMapper.map(agendamentoSalvo, AgendamentoDTOResponse.class);
    }
}
