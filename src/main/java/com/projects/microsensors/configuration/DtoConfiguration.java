package com.projects.microsensors.configuration;

import com.projects.microsensors.model.SensorDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DtoConfiguration {
    @Bean
    public SensorDTO getNewDto(){
        return new SensorDTO();
    }
}
