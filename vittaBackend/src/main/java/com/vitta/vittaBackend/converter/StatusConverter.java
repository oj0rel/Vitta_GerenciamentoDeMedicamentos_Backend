package com.vitta.vittaBackend.converter;

import com.vitta.vittaBackend.enums.UsuarioStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<UsuarioStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UsuarioStatus status) {
        return status != null ? status.getCode() : null;
    }

    @Override
    public UsuarioStatus convertToEntityAttribute(Integer codigo) {
        if (codigo == null) {
            return UsuarioStatus.INATIVO;
        }
        for (UsuarioStatus status : UsuarioStatus.values()) {
            if (status.getCode().equals(codigo)) {
                return status;
            }
        }

        return UsuarioStatus.INATIVO;
    }
}
