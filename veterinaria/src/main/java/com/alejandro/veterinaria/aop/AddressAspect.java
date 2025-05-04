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

    // To create advice that intercepts the method 'save' for the address entity
    @Before("execution(* com.alejandro.veterinaria.services.ClientServiceImp.saveAddressByClientId(..))") 
    public void trimBefore(JoinPoint joinPoint) {

        logger.info("Aspect executing before the method save() ------------------------");

        Object[] args = joinPoint.getArgs(); // Get the argument of the method to be intercepted
        Address addressBefore = (Address) args[1]; // Cast the argument to type Address

        this.cleanSpaces(addressBefore);
    }

    // To create advice that intercepts the method 'update' for the address entity
    @Before("execution( * com.alejandro.veterinaria.services.ClientServiceImp.editAddressByClientId(..))")  
    public void trimBeforeUpdate(JoinPoint joinPoint) {

        logger.info("Aspect executing before the method update() ------------------------");

        Object[] args = joinPoint.getArgs(); // Get the argument of the method to be intercepted
        Address addressBefore = (Address) args[1]; // Cast the argument to type Address

        this.cleanSpaces(addressBefore);
    }

    // To remove the blanks in this object
    private void cleanSpaces(Address addressBefore) {

        addressBefore.setStreet(addressBefore.getStreet().trim());
        addressBefore.setState(addressBefore.getState().trim());
        addressBefore.setCity(addressBefore.getCity().trim());

    }

}
