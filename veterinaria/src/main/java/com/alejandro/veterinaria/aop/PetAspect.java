package com.alejandro.veterinaria.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alejandro.veterinaria.entities.Pet;

@Aspect
@Component
public class PetAspect {

    private static final Logger logger = LoggerFactory.getLogger(PetAspect.class);

    // To create advice that intercepts the save method of the pet service file
    @Before("execution(public com.alejandro.veterinaria.entities.Client com.alejandro.veterinaria.services.ClientServiceImp.savePetByClientId(com.alejandro.veterinaria.entities.Client, com.alejandro.veterinaria.entities.Pet))")    public void trimBefore(JoinPoint joinPoint) {

        logger.info("Aspecto ejecutado antes del método savePetByClientId() ------------------------");

        Object[] args = joinPoint.getArgs(); // Obtiene el argumento del método interceptado
        Pet petBefore = (Pet) args[1]; // Cast del argumento al tipo Pet

        petBefore.setName(petBefore.getName().trim());
        petBefore.setSpecie(petBefore.getSpecie().trim());
        petBefore.setBreed(petBefore.getBreed() != null ? petBefore.getBreed().trim() : null);
    }
}
