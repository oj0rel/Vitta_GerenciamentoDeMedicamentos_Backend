package com.vitta.vittaBackend.config;

import com.vitta.vittaBackend.dto.response.usuario.UsuarioDTOResponse;
import com.vitta.vittaBackend.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuração para ignorar campos nulos
//        modelMapper.getConfiguration().setSkipNullEnabled(true);

        //ele vai ignorar os campos uma única vez aqui
//        modelMapper.typeMap(Usuario.class, UsuarioDTOResponse.class)
//                .addMappings(mapper -> {
//                    mapper.skip(UsuarioDTOResponse::setMedicamentos);
//                    mapper.skip(UsuarioDTOResponse::setAgendamentos);
//                });

        return modelMapper;
    }

}
