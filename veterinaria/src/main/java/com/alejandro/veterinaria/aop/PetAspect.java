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

    // To create advice that intercepts the method 'save' for the pet entity
    @Before("execution( * com.alejandro.veterinaria.services.ClientServiceImp.savePetByClientId(..))")  
    public void trimBeforeSave(JoinPoint joinPoint) {

        logger.info("Aspect executing before the method save() ------------------------");

        Object[] args = joinPoint.getArgs(); // Get the argument of the method to be intercepted
        Pet petBefore = (Pet) args[1]; // Cast the argument to type Pet

        this.cleanSpaces(petBefore);
    }

    // To create advice that intercepts the method 'update' for the pet entity
    @Before("execution( * com.alejandro.veterinaria.services.ClientServiceImp.editPetByClientId(..))")  
    public void trimBeforeUpdate(JoinPoint joinPoint) {

        logger.info("Aspect executing before the method update() ------------------------");

        Object[] args = joinPoint.getArgs(); // Get the argument of the method to be intercepted
        Pet petBefore = (Pet) args[2]; // Cast the argument to type Pet

        this.cleanSpaces(petBefore);
    }

    // To remove the blanks in this object
    private void cleanSpaces(Pet petBefore) {

        petBefore.setName(petBefore.getName().trim());
        petBefore.setSpecie(petBefore.getSpecie().trim());
        petBefore.setBreed(petBefore.getBreed() != null ? petBefore.getBreed().trim() : null);
        petBefore.setReasonForVisit(petBefore.getReasonForVisit().trim());

    }

}
