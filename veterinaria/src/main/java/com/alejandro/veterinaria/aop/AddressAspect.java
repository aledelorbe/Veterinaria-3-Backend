package com.alejandro.veterinaria.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alejandro.veterinaria.entities.Address;

@Aspect
@Component
public class AddressAspect {

    private static final Logger logger = LoggerFactory.getLogger(AddressAspect.class);

    // To create advice that intercepts the saveAddressByClientId method of the address service file
    @Before("execution(public com.alejandro.veterinaria.entities.Client com.alejandro.veterinaria.services.ClientServiceImp.saveAddressByClientId(com.alejandro.veterinaria.entities.Client, com.alejandro.veterinaria.entities.Address))")    
    public void trimBefore(JoinPoint joinPoint) {

        logger.info("Aspecto ejecutado antes del método saveAddressByClientId() ------------------------");

        Object[] args = joinPoint.getArgs(); // Obtiene el argumento del método interceptado
        Address addressBefore = (Address) args[1]; // Cast del argumento al tipo Address

        addressBefore.setStreet(addressBefore.getStreet().trim());
        addressBefore.setState(addressBefore.getState().trim());
        addressBefore.setCity(addressBefore.getCity().trim());
    }
}
