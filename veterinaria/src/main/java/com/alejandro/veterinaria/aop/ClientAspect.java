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

    // To create advice that intercepts the method 'save' for the client entity
    @Before("execution( * com.alejandro.veterinaria.services.ClientServiceImp.save(..))")
    public void trimBefore(JoinPoint joinPoint) {

        logger.info("Aspect executing before the method save() ------------------------");

        Object[] args = joinPoint.getArgs(); // Get the argument of the method to be intercepted
        Client clientBefore = (Client) args[0]; // Cast the argument to type Client

        this.cleanSpaces(clientBefore);

    }

    // To create advice that intercepts the method 'update' for the client entity
    @Before("execution( * com.alejandro.veterinaria.services.ClientServiceImp.update(..))")  
    public void trimBeforeUpdate(JoinPoint joinPoint) {

        logger.info("Aspect executing before the method update() ------------------------");

        Object[] args = joinPoint.getArgs(); // Get the argument of the method to be intercepted
        Client clientBefore = (Client) args[1]; // Cast the argument to type Client

        this.cleanSpaces(clientBefore);
    }

    // To remove the blanks in this object
    private void cleanSpaces(Client clientBefore) {

        clientBefore.setName(clientBefore.getName().trim());
        clientBefore.setLastname(clientBefore.getLastname().trim());
        clientBefore.setEmail(clientBefore.getEmail().trim());

    }

}
