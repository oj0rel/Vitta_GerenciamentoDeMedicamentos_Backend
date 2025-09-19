package com.vitta.vittaBackend.converter;

import com.vitta.vittaBackend.enums.GeralStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GeralStatusConverter implements AttributeConverter<GeralStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(GeralStatus geralStatus) {
        return geralStatus != null ? geralStatus.getCode() : null;
    }

    @Override
    public GeralStatus convertToEntityAttribute(Integer code) {
        if (code == null) {
            return GeralStatus.INATIVO;
        }
        for (GeralStatus geralStatus : GeralStatus.values()) {
            if (geralStatus.getCode().equals(code)) {
                return geralStatus;
            }
        }

        return GeralStatus.INATIVO;
    }
}
