package com.alejandro.veterinaria.controllers;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alejandro.veterinaria.entities.ErrorMessage;

// This class is used to handle when an exception is fired 
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<ErrorMessage> duplicate(Exception e) {

        // To know which entity fires the 'DataIntegrityViolationException'
        // The first part of this condition is for Mysql and the other one is for H2
        String errorMessage;
        if (e.getMessage().contains("client.UK_client") || e.getMessage().contains("PUBLIC.UK_CLIENT")) {
            // To know if this exception is fired by an update or create action.
            if (e.getMessage().contains("insert")) {
                errorMessage = "Error! El cliente que se desea registrar ya se encuentra en la base de datos.";
            } else {
                errorMessage = "Error! Este nombre de cliente al cual se desea actualizar ya lo posee otro cliente.";
            }
        } else {
            // To know if this exception is fired by an update or create action.
            if (e.getMessage().contains("insert")) {
                errorMessage = "Error! Esta mascota ya se registro previamente para este cliente.";
            } else {
                errorMessage = "Error! Este nombre de mascota al cual se desea actualizar ya lo posee otro mascota de este mismo cliente.";
            }
        }

        ErrorMessage error = new ErrorMessage();
        error.setDateTime(LocalDateTime.now());
        error.setError(errorMessage);
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.CONFLICT.value());

        return ResponseEntity.internalServerError().body(error);
    }
}
