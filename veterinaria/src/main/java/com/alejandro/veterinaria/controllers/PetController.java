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

    // To create an endpoint that allows invoking the method 'getPetsByClientId'.
    @GetMapping("/{id_client}/pets")
    public ResponseEntity<?> getPetsByClientId(@PathVariable Long id_client) {
        // Search for a specific client and if it's present then return it.
        Optional<Client> optionalClient = clientService.findById(id_client);

        if (optionalClient.isPresent()) {
            return ResponseEntity.ok(service.getPetsByClientId(optionalClient.get()));
        }

        // Else returns code response 404
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

        // Search for a specific client if it exists then save the pet
        Optional<Client> optionalClient = clientService.findById(clientId);

        if (optionalClient.isPresent()) {
            Client newClient = service.savePetByClientId(optionalClient.get(), newPet);
            return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
        }

        // Else returns code response 404
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows updating information of a certain pet of a
    // certain client
    @PutMapping("/{clientId}/pets/{petId}")
    public ResponseEntity<?> editPetByClientId(@Valid @RequestBody Pet editPet, BindingResult result,
            @PathVariable Long clientId, @PathVariable Long petId) {
        // To handle of obligations of object attributes
        if (result.hasFieldErrors()) {
            return utilValidation.validation(result);
        }

        // Search for a specific client and specific pet and if they are present then
        // edit the information about pet
        Optional<Client> optionalClient = clientService.findById(clientId);
        Optional<Pet> optionalPet = service.findById(petId);

        if ( optionalClient.isPresent() && optionalPet.isPresent() ) {
            Optional<Client> optionalUpdateClient = service.editPetByClientId(optionalClient.get(), petId, editPet);

            // If the 'Optional Client Update' object is present, the pet was updated.
            if (optionalUpdateClient.isPresent()) {
                Client updateClient = optionalUpdateClient.get();
                return ResponseEntity.status(HttpStatus.CREATED).body(updateClient);
            } else {
                // Else returns code response 404
                return ResponseEntity.notFound().build();
            }
        }

        // Else returns code response 404
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows deleting a certain pet of a certain client
    @DeleteMapping("/{clientId}/pets/{petId}")
    public ResponseEntity<?> deletePetByClientId(@PathVariable Long clientId, @PathVariable Long petId) {

        // Search for a specific client and specific pet and if they are present then
        // delete a pet
        Optional<Client> optionalClient = clientService.findById(clientId);
        Optional<Pet> optionalPet = service.findById(petId);

        if (optionalClient.isPresent() && optionalPet.isPresent()) {
            Optional<Client> optionalUpdateClient = service.deletePetByClientId(optionalClient.get(), petId);

            // If the 'Optional Client Update' object is present, the pet was deleted.
            if (optionalUpdateClient.isPresent()) {
                Client updateClient = optionalUpdateClient.get();

                return ResponseEntity.ok(updateClient);
            } else {
                // Else returns code response 404
                return ResponseEntity.notFound().build();
            }
        }

        // Else returns code response 404
        return ResponseEntity.notFound().build();
    }

}
