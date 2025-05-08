package com.alejandro.veterinaria;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.alejandro.veterinaria.utils.UtilValidation;

// We use this class to create components in the test context
@TestConfiguration
public class TestConfig {
    
    // Create the component that represents the real UtilValidation class
    @Bean
    public UtilValidation utilValidation() {
        return new UtilValidation();
    }
    
}
