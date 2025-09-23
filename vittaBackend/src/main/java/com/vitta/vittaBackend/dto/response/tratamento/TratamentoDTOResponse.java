package com.vitta.vittaBackend.dto.response.tratamento;

import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoResumoDTOResponse;
import com.vitta.vittaBackend.dto.response.usuario.UsuarioResumoDTOResponse;
import com.vitta.vittaBackend.entity.Tratamento;
import com.vitta.vittaBackend.enums.TipoFrequencia;
import com.vitta.vittaBackend.enums.TratamentoStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TratamentoDTOResponse {

    /**
     * O ID único do tratamento.
     */
    private Integer id;

    /**
     * A dosagem a ser administrada em cada toma.
     */
    private BigDecimal dosagem;

    /**
     * Instruções de uso em formato de texto livre.
     */
    private String instrucoes;

    /**
     * A data de início do tratamento.
     */
    private LocalDate dataDeInicio;

    /**
     * A data de término do tratamento.
     */
    private LocalDate dataDeTermino;

    /**
     * O tipo de regra para a frequência das tomas (ex: INTERVALO_HORAS).
     */
    private TipoFrequencia tipoDeFrequencia;

    /**
     * O intervalo em horas entre as doses (aplicável apenas se o tipo de frequência for INTERVALO_HORAS).
     */
    private Integer intervaloEmHoras;

    /**
     * A lista de horários específicos (ex: "08:00,16:00").
     */
    private String horariosEspecificos;

    /**
     * O status atual do tratamento (ex: ATIVO, CONCLUIDO).
     */
    private TratamentoStatus status;

    /**
     * DTO resumido do usuário ao qual este tratamento pertence.
     */
    private UsuarioResumoDTOResponse usuario;

    /**
     * DTO resumido do medicamento que faz parte deste tratamento.
     */
    private MedicamentoResumoDTOResponse medicamento;

    /**

     * Construtor vazio.
     */
    public TratamentoDTOResponse() {
    }

    /**
     * Construtor de mapeamento a partir da entidade Tratamento.
     * Facilita a conversão da entidade e suas relações para este DTO.
     * @param tratamentoEntity A entidade vinda do banco de dados.
     */
    public TratamentoDTOResponse(Tratamento tratamentoEntity) {
        this.id = tratamentoEntity.getId();
        this.dosagem = tratamentoEntity.getDosagem();
        this.instrucoes = tratamentoEntity.getInstrucoes();
        this.dataDeInicio = tratamentoEntity.getDataDeInicio();
        this.dataDeTermino = tratamentoEntity.getDataDeTermino();
        this.tipoDeFrequencia = tratamentoEntity.getTipoDeFrequencia();
        this.intervaloEmHoras = tratamentoEntity.getIntervaloEmHoras();
        this.horariosEspecificos = tratamentoEntity.getHorariosEspecificos();
        this.status = tratamentoEntity.getStatus();

        if (tratamentoEntity.getUsuario() != null) {
            this.usuario = new UsuarioResumoDTOResponse(tratamentoEntity.getUsuario());
        }

        if (tratamentoEntity.getMedicamento() != null) {
            this.medicamento = new MedicamentoResumoDTOResponse(tratamentoEntity.getMedicamento());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDosagem() {
        return dosagem;
    }

    public void setDosagem(BigDecimal dosagem) {
        this.dosagem = dosagem;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }

    public LocalDate getDataDeInicio() {
        return dataDeInicio;
    }

    public void setDataDeInicio(LocalDate dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
    }

    public LocalDate getDataDeTermino() {
        return dataDeTermino;
    }

    public void setDataDeTermino(LocalDate dataDeTermino) {
        this.dataDeTermino = dataDeTermino;
    }

    public TipoFrequencia getTipoDeFrequencia() {
        return tipoDeFrequencia;
    }

    public void setTipoDeFrequencia(TipoFrequencia tipoDeFrequencia) {
        this.tipoDeFrequencia = tipoDeFrequencia;
    }

    public Integer getIntervaloEmHoras() {
        return intervaloEmHoras;
    }

    public void setIntervaloEmHoras(Integer intervaloEmHoras) {
        this.intervaloEmHoras = intervaloEmHoras;
    }

    public String getHorariosEspecificos() {
        return horariosEspecificos;
    }

    public void setHorariosEspecificos(String horariosEspecificos) {
        this.horariosEspecificos = horariosEspecificos;
    }

    public TratamentoStatus getStatus() {
        return status;
    }

    public void setStatus(TratamentoStatus status) {
        this.status = status;
    }

    public UsuarioResumoDTOResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResumoDTOResponse usuario) {
        this.usuario = usuario;
    }

    public MedicamentoResumoDTOResponse getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(MedicamentoResumoDTOResponse medicamento) {
        this.medicamento = medicamento;
    }
}
