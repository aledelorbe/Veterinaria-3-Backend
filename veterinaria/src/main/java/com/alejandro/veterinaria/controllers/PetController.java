package com.alejandro.veterinaria.controllers;

import java.util.Optional;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.entities.Pet;
import com.alejandro.veterinaria.services.ClientService;
import com.alejandro.veterinaria.services.PetService;

import jakarta.validation.Valid;

@RestController // To create a api rest.
@RequestMapping("/api/pets") // To create a base path.
public class PetController {

    // To Inject the service dependency
    @Autowired
    private PetService service;

    // To Inject the service dependency
    @Autowired
    private ClientService clientService;

    // To create an endpoint that allows getting all of the pets of an certain
    // client
    // @GetMapping("/{clientId}")
    // public List<Pet> getAllOfPetsByClientId(@PathVariable Long clientId) {
    // return service.findAllOfPetsByClientId(clientId);
    // }

    // To create an endpoint that allows save a new pet of an certain
    // client
    @PostMapping("/{clientId}")
    public ResponseEntity<?> saveNewPetByClientId(@Valid @RequestBody Pet newPet, BindingResult result, @PathVariable Long clientId) {
        
        // Search a specific client and if it's present then return it.
        Optional<Client> optionalClient = clientService.findById(clientId);
        
        if (optionalClient.isPresent()) {
            service.savePetByClientId(optionalClient.get(), newPet);
            return ResponseEntity.ok().build();
        }
        // Else returns code response 404
        return ResponseEntity.notFound().build();
    }
}
