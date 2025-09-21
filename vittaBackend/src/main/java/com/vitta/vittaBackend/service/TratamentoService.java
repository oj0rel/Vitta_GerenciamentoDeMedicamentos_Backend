package com.vitta.vittaBackend.service;

import com.vitta.vittaBackend.dto.request.tratamento.TratamentoDTORequest;
import com.vitta.vittaBackend.entity.Agendamento;
import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.entity.Tratamento;
import com.vitta.vittaBackend.entity.Usuario;
import com.vitta.vittaBackend.enums.TipoFrequencia;
import com.vitta.vittaBackend.enums.agendamento.AgendamentoStatus;
import com.vitta.vittaBackend.enums.agendamento.TipoDeAlerta;
import com.vitta.vittaBackend.repository.AgendamentoRepository;
import com.vitta.vittaBackend.repository.MedicamentoRepository;
import com.vitta.vittaBackend.repository.TratamentoRepository;
import com.vitta.vittaBackend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TratamentoService {

    private final TratamentoRepository tratamentoRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TratamentoService(
            TratamentoRepository tratamentoRepository,
            AgendamentoRepository agendamentoRepository,
            MedicamentoRepository medicamentoRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.tratamentoRepository = tratamentoRepository;
        this.agendamentoRepository = agendamentoRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Tratamento cadastrarTratamento(TratamentoDTORequest tratamentoDTORequest) {

        // linkar com Medicamento e Usuario já cadastrados passando o ID no DTORequest
        Medicamento medicamento = medicamentoRepository.findById(tratamentoDTORequest.getMedicamentoId()).orElseThrow();
        Usuario usuario = usuarioRepository.findById(tratamentoDTORequest.getUsuarioId()).orElseThrow();

        Tratamento tratamento = new Tratamento();
        tratamento.setMedicamento(medicamento);
        tratamento.setUsuario(usuario);
        tratamento.setDosagem(tratamentoDTORequest.getDosagem());
        tratamento.setInstrucoes(tratamentoDTORequest.getInstrucoes());
        tratamento.setDataDeInicio(tratamentoDTORequest.getDataDeInicio());
        tratamento.setDataDeTermino(tratamentoDTORequest.getDataDeTermino());

        if (tratamentoDTORequest.getTipoDeFrequencia() != null) {
            tratamento.setTipoDeFrequencia(
                    TipoFrequencia.fromCodigo(tratamentoDTORequest.getTipoDeFrequencia())
            );
        }

        tratamento.setIntervaloEmHoras(tratamentoDTORequest.getIntervaloEmHoras());
        tratamento.setHorariosEspecificos(tratamentoDTORequest.getHorariosEspecificos());

        Tratamento tratamentoSalvo = tratamentoRepository.save(tratamento);

        // gerar e salvar os agendamentos
        List<Agendamento> agendamentos = gerarAgendamentosParaTratamento(tratamentoSalvo);
        agendamentoRepository.saveAll(agendamentos);

        return tratamentoSalvo;
    }

    private List<Agendamento> gerarAgendamentosParaTratamento(Tratamento tratamento) {
        List<Agendamento> agendamentos = new ArrayList<>();
        LocalDate dataCorrente = tratamento.getDataDeInicio();

        while (!dataCorrente.isAfter(tratamento.getDataDeTermino())) {
            if (tratamento.getTipoDeFrequencia() == TipoFrequencia.INTERVALO_HORAS) {
                // lógica para intervalo de horas (ex: a cada 8 horas, começando às 8h)
                LocalTime proximoHorario = LocalTime.of(8, 0); // horário de início padrão
                while (proximoHorario.isBefore(LocalTime.MAX)) {
                    LocalDateTime dataHoraAgendamento = LocalDateTime.of(dataCorrente, proximoHorario);
                    agendamentos.add(criarAgendamento(tratamento, dataHoraAgendamento));
                    proximoHorario = proximoHorario.plusHours(tratamento.getIntervaloEmHoras());
                }
            } else if (tratamento.getTipoDeFrequencia() == TipoFrequencia.HORARIOS_ESPECIFICOS) {
                // lógica para horários específicos (ex: "08:00,14:00,22:00")
                String[] horarios = tratamento.getHorariosEspecificos().split(",");
                for (String horaStr : horarios) {
                    LocalTime horario = LocalTime.parse(horaStr.trim());
                    LocalDateTime dataHoraAgendamento = LocalDateTime.of(dataCorrente, horario);
                    agendamentos.add(criarAgendamento(tratamento, dataHoraAgendamento));
                }
            }
            dataCorrente = dataCorrente.plusDays(1);
        }
        return agendamentos;
    }

    private Agendamento criarAgendamento(Tratamento tratamento, LocalDateTime dataHora) {
        Agendamento agendamento = new Agendamento();
        agendamento.setTratamento(tratamento);
        agendamento.setHorarioDoAgendamento(dataHora);
        agendamento.setStatus(AgendamentoStatus.PENDENTE);

        return agendamento;
    }
}
