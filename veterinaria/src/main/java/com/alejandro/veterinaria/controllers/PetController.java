package com.alejandro.veterinaria.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.veterinaria.entities.Pet;
import com.alejandro.veterinaria.services.PetService;

import jakarta.validation.Valid;

@RestController // To create a api rest.
@RequestMapping("/api/pets") // To create a base path.
public class PetController {

    // To Inject the service dependency
    @Autowired
    private PetService service;

    // To create an endpoint that allows edit information of a pet
    @PutMapping("/{petId}")
    public ResponseEntity<?> editPet(@Valid @RequestBody Pet editPet, BindingResult result, @PathVariable Long petId) {

        // Search a specific client and specific pet
        Optional<Pet> optionalPet = service.findById(petId);

        if ( optionalPet.isPresent() ) {
            Pet updatePet = service.updatePet(optionalPet.get(), editPet);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatePet);
        }
        // Else returns code response 404
        return ResponseEntity.notFound().build();
    }
}
