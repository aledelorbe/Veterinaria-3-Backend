package com.alejandro.veterinaria;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.alejandro.veterinaria.services.ClientService;
import com.alejandro.veterinaria.utils.UtilValidation;

// We use this class to create components in the test context
@TestConfiguration
public class TestConfig {
    
    // Create the component that represents the service to mock
    @Bean
    public ClientService clientService() {
        return Mockito.mock(ClientService.class);
    }

    // Create the component that represents the real UtilValidation class
    @Bean
    public UtilValidation utilValidation() {
        return new UtilValidation();
    }
    
}
