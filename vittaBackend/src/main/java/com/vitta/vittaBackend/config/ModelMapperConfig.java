package com.vitta.vittaBackend.config;

import com.vitta.vittaBackend.dto.request.medicamento.MedicamentoDTORequest;
import com.vitta.vittaBackend.dto.response.medicamento.MedicamentoDTOResponse;
import com.vitta.vittaBackend.entity.Medicamento;
import com.vitta.vittaBackend.enums.OrderStatus;
import com.vitta.vittaBackend.enums.medicamento.TipoUnidadeDeMedida;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuração para ignorar campos nulos
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        // ================================
        // Conversores
        // ================================
        // Entidade -> ResponseDTO (Enum -> String)
        Converter<TipoUnidadeDeMedida, String> tipoUnidadeToString = ctx ->
                ctx.getSource() != null ? ctx.getSource().name() : null;

        Converter<OrderStatus, String> statusToString = ctx ->
                ctx.getSource() != null ? ctx.getSource().name() : null;

        // RequestDTO -> Entidade (Integer/String -> Enum)
        Converter<Integer, TipoUnidadeDeMedida> codigoToTipoUnidade = ctx ->
                ctx.getSource() != null ? TipoUnidadeDeMedida.fromCodigo(ctx.getSource()) : null;

        Converter<String, TipoUnidadeDeMedida> stringToTipoUnidade = ctx ->
                ctx.getSource() != null ? TipoUnidadeDeMedida.valueOf(ctx.getSource()) : null;

        Converter<Integer, OrderStatus> codigoToStatus = ctx ->
                ctx.getSource() != null ? OrderStatus.fromCodigo(ctx.getSource()) : null;

        // ================================
        // Mapeamentos
        // ================================
        // Entidade -> ResponseDTO
        modelMapper.typeMap(Medicamento.class, MedicamentoDTOResponse.class).addMappings(mapper -> {
            mapper.using(tipoUnidadeToString)
                    .map(Medicamento::getTipoUnidadeDeMedida, MedicamentoDTOResponse::setTipoUnidadeDeMedida);

            mapper.using(statusToString)
                    .map(Medicamento::getStatusTipo, MedicamentoDTOResponse::setStatus);
        });

        // RequestDTO -> Entidade
        modelMapper.typeMap(MedicamentoDTORequest.class, Medicamento.class).addMappings(mapper -> {
            mapper.using(codigoToTipoUnidade)
                    .map(MedicamentoDTORequest::getTipoUnidadeDeMedida, Medicamento::setTipoUnidadeDeMedida);

            mapper.using(codigoToStatus)
                    .map(MedicamentoDTORequest::getStatus, Medicamento::setStatusTipo);
        });

        return modelMapper;
    }

}
