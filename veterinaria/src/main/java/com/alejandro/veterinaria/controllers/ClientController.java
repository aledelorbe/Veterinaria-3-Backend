package com.alejandro.veterinaria.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import jakarta.validation.Valid;

@RestController // To create a api rest.
@RequestMapping("/api/clients") // To create a base path.
public class ClientController {

    // To Inject the service dependency
    @Autowired
    private ClientService service;

    // To Inject the service dependency
    @Autowired
    private PetService petService;

    // -----------------------------
    // Methods for client entity
    // -----------------------------

    // To create an endpoint that allows invocating the method findAll.
    @GetMapping()
    public List<Client> clients() {
        return service.findAll();
    }

    // To create an endpoint that allows invocating the method fingById.
    @GetMapping("/{id}")
    public ResponseEntity<?> client(@PathVariable Long id) {
        // Search a specific client and if it's present then return it.
        Optional<Client> optionalClient = service.findById(id);
        if (optionalClient.isPresent()) {
            return ResponseEntity.ok(optionalClient.orElseThrow());
        }
        // Else returns code response 404
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows invocating the method save.
    // The annotation called 'RequestBody' allows receiving data of a client
    @PostMapping()
    public ResponseEntity<?> saveClient(@Valid @RequestBody Client client, BindingResult result) {
        // To handle the obligations of object attributes
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        // When a new client is created to respond return the same client
        Client newClient = service.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    // To create an endpoint that allows update all of atributte values a specific
    // client based its id.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@Valid @RequestBody Client client, BindingResult result,
            @PathVariable Long id) {
        // To handle of obligations of object attributes
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        // Find specific client and if it's present then return specific client
        Optional<Client> optionalClient = service.update(id, client);

        if (optionalClient.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalClient.orElseThrow());
        }
        // Else return code response 404
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows deleting a specific client based its id.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        // Find specific client and if it's present then return specific client
        Optional<Client> optionalClient = service.deleteById(id);
        if (optionalClient.isPresent()) {
            return ResponseEntity.ok(optionalClient.orElseThrow());
        }
        // Else return code response 404
        return ResponseEntity.notFound().build();
    }

    // -----------------------------
    // Methods for pet entity
    // -----------------------------

    // To create an endpoint that allows save a new pet of an certain
    // client
    @PostMapping("/{clientId}/pets")
    public ResponseEntity<?> saveNewPetByClientId(@Valid @RequestBody Pet pet, BindingResult result,
            @PathVariable Long clientId) {
        // To handle of obligations of object attributes
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        // Search a specific client
        Optional<Client> optionalClient = service.findById(clientId);

        if (optionalClient.isPresent()) {
            Client newClient = service.savePetByClientId(optionalClient.get(), pet);
            return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
        }
        // Else returns code response 404
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows edit information of a certain pet of an certain
    // client
    @PutMapping("/{clientId}/pets/{petId}")
    public ResponseEntity<?> editPetByClientId(@Valid @RequestBody Pet pet, BindingResult result, @PathVariable Long clientId, @PathVariable Long petId) {
        // To handle of obligations of object attributes
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        // Search a specific client and specific pet
        Optional<Client> optionalClient = service.findById(clientId);
        Optional<Pet> optionalPet = petService.findById(petId);

        if (optionalClient.isPresent() && optionalPet.isPresent()) {
            Client updateClient = service.editPetByClientId(optionalClient.get(), optionalPet.get(), pet);
            return ResponseEntity.status(HttpStatus.CREATED).body(updateClient);
        }
        // Else returns code response 404
        return ResponseEntity.notFound().build();
    }
    
    // To create an endpoint that allows save a new pet of an certain
    // client
    @DeleteMapping("/{clientId}/pets/{petId}")
    public ResponseEntity<?> deletePetByClientId(@PathVariable Long clientId, @PathVariable Long petId) {

        // Search a specific client and specific pet
        Optional<Client> optionalClient = service.findById(clientId);
        Optional<Pet> optionalPet = petService.findById(petId);

        if (optionalClient.isPresent() && optionalPet.isPresent()) {
            Client updateClient = service.deletePetByClientId(optionalClient.get(), optionalPet.get());
            return ResponseEntity.ok(updateClient);
        }
        // Else returns code response 404
        return ResponseEntity.notFound().build();
    }

    // -----------------------------
    // Method to validate
    // -----------------------------

    // To send a JSON object with messages about the obligations of each object
    // attribute
    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(e -> {
            errors.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
