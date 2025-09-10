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

        return modelMapper;
    }

}
