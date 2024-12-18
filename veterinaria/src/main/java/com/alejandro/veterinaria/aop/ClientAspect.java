package com.alejandro.veterinaria.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alejandro.veterinaria.entities.Client;

@Aspect
@Component
public class ClientAspect {

    private static final Logger logger = LoggerFactory.getLogger(ClientAspect.class);

    // To create advice that intercepts the save method of the client service file
    @Before("execution(public com.alejandro.veterinaria.entities.Client com.alejandro.veterinaria.services.ClientService.save(com.alejandro.veterinaria.entities.Client))")
    public void trimBefore(JoinPoint joinPoint) {

        logger.info("Aspecto ejecutado antes del método save() ------------------------");

        Object[] args = joinPoint.getArgs(); // Obtiene el argumento del método interceptado
        Client clientBefore = (Client) args[0]; // Cast del argumento al tipo Client

        clientBefore.setName(clientBefore.getName().trim());
        clientBefore.setLastname(clientBefore.getLastname().trim());
        clientBefore.setEmail(clientBefore.getEmail().trim());
    }
}
