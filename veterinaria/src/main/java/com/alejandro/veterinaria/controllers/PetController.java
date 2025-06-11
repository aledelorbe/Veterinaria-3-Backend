package com.alejandro.veterinaria.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.entities.Pet;
import com.alejandro.veterinaria.services.ClientService;
import com.alejandro.veterinaria.services.PetService;
import com.alejandro.veterinaria.utils.UtilValidation;

import jakarta.validation.Valid;

@RestController // To create a api rest.
@RequestMapping("/api/clients") // To create a base path.
public class PetController {
    
    // To Inject the service dependency
    @Autowired
    private ClientService clientService;

    // To Inject the service dependency
    @Autowired
    private PetService service;

    @Autowired
    private UtilValidation utilValidation;

    // -----------------------------
    // Methods for pet entity
    // -----------------------------

    // To create an endpoint that allows getting all of the pets of a specif client
    @GetMapping("/{id_client}/pets")
    public ResponseEntity<?> getPetsByClient(@PathVariable Long id_client) {
        // Search for a specific client
        Optional<Client> optionalClient = clientService.findById(id_client);

        // if the client is present then return the pet array.
        if (optionalClient.isPresent()) {
            return ResponseEntity.ok(optionalClient.get().getPets());
        }

        // Else, return an empty optional
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows saving a new pet of an certain client
    @PostMapping("/{clientId}/pets")
    public ResponseEntity<?> saveNewPetByClientId(@Valid @RequestBody Pet newPet, BindingResult result,
            @PathVariable Long clientId) {
        // To handle of obligations of object attributes
        if (result.hasFieldErrors()) {
            return utilValidation.validation(result);
        }

        // Call the 'savePetByClient' method
        Optional<Client> optionalNewClient = service.savePetByClient(clientId, newPet);

        // if the client is present then it means that the object could be saved
        if (optionalNewClient.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalNewClient.get());
        }

        // Else, return an empty optional
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows updating information of a certain pet of a
    // certain client
    @PutMapping("/{clientId}/pets/{petId}")
    public ResponseEntity<?> editPetByClient(@Valid @RequestBody Pet editPet, BindingResult result,
            @PathVariable Long clientId, @PathVariable Long petId) {
        // To handle of obligations of object attributes
        if (result.hasFieldErrors()) {
            return utilValidation.validation(result);
        }

        // Call the 'editPetByClient' method
        Optional<Client> optionalUpdateClient = service.editPetByClient(clientId, petId, editPet);

        // if the client is present then it means that the object could be updated
        if ( optionalUpdateClient.isPresent() ) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUpdateClient.get());
        }

        // Else, return a 404 status code.
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows deleting a certain pet of a certain client
    @DeleteMapping("/{clientId}/pets/{petId}")
    public ResponseEntity<?> deletePetByClient(@PathVariable Long clientId, @PathVariable Long petId) {

        // Call the 'deletePetByClient' method
        Optional<Client> optionalUpdateClient = service.deletePetByClient(clientId, petId);

        // if the client is present then it means that the object could be deleted
        if ( optionalUpdateClient.isPresent() ) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalUpdateClient.get());
        }

        // Else, return an empty optional
        return ResponseEntity.notFound().build();
    }

}
