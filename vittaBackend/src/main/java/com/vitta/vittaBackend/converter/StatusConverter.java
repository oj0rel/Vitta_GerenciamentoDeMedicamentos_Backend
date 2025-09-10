package com.vitta.vittaBackend.converter;

import com.vitta.vittaBackend.enums.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<OrderStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrderStatus status) {
        return status != null ? status.getCode() : null;
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer codigo) {
        if (codigo == null) {
            return OrderStatus.INATIVO;
        }
        for (OrderStatus status : OrderStatus.values()) {
            if (status.getCode().equals(codigo)) {
                return status;
            }
        }

        return OrderStatus.INATIVO;
    }
}
