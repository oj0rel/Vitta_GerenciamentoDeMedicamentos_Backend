package com.vitta.vittaBackend.converter;

import com.vitta.vittaBackend.enums.medicamentoHistorico.UsoMedicamentoStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UsoMedicamentoStatusConverter implements AttributeConverter<UsoMedicamentoStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UsoMedicamentoStatus usoMedicamentoStatus) {
        return usoMedicamentoStatus != null ? usoMedicamentoStatus.getCode() : null;
    }

    @Override
    public UsoMedicamentoStatus convertToEntityAttribute(Integer code) {
        if (code == null) {
            return UsoMedicamentoStatus.INATIVO;
        }
        for (UsoMedicamentoStatus usoMedicamentoStatus : UsoMedicamentoStatus.values()) {
            if (usoMedicamentoStatus.getCode().equals(code)) {
                return usoMedicamentoStatus;
            }
        }

        return UsoMedicamentoStatus.INATIVO;
    }
}
