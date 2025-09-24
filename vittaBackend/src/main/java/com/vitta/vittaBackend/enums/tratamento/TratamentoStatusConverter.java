package com.vitta.vittaBackend.enums.tratamento;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TratamentoStatusConverter implements AttributeConverter<TratamentoStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TratamentoStatus status) {
        if (status == null) {
            return null;
        }

        return status.getCodigo();
    }

    @Override
    public TratamentoStatus convertToEntityAttribute(Integer codigo) {
        if (codigo == null) {
            return null;
        }

        return TratamentoStatus.fromCodigo(codigo);
    }
}