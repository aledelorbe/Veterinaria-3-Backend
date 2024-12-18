package com.alejandro.veterinaria.controllers;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alejandro.veterinaria.entities.ErrorMessage;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<ErrorMessage> clientDuplicate(Exception e) {
        ErrorMessage error = new ErrorMessage();
        error.setDateTime(LocalDateTime.now());
        error.setError("Error! El cliente que se desea registrar ya se encuentra en la base de datos.");
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.CONFLICT.value());

        return ResponseEntity.internalServerError().body(error);
    }
}
