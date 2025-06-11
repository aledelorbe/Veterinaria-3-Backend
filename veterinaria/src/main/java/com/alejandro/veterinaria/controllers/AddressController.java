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

import com.alejandro.veterinaria.entities.Address;
import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.services.AddressService;
import com.alejandro.veterinaria.services.ClientService;
import com.alejandro.veterinaria.utils.UtilValidation;

import jakarta.validation.Valid;

@RestController // To create a api rest.
@RequestMapping("/api/clients") // To create a base path.
public class AddressController {

    // To Inject the service dependency
    @Autowired
    private AddressService service;

    // To Inject the service dependency
    @Autowired
    private ClientService clientService;

    @Autowired
    private UtilValidation utilValidation;
    
    // -----------------------------
    // Methods for address entity
    // -----------------------------

    // To create an endpoint that allows invoking the 'getAddressByClient' method.
    @GetMapping("/{id_client}/addresses")
    public ResponseEntity<?> getAddressByClient(@PathVariable Long id_client) {
        // Search for a specific client
        Optional<Client> optionalClient = clientService.findById(id_client);

        // if the client is present then return the pet array.
        if (optionalClient.isPresent()) {
            return ResponseEntity.ok(optionalClient.get().getAddress());
        }

        // Else, return an empty optional
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows saving a new address of an certain client
    @PostMapping("/{clientId}/addresses")
    public ResponseEntity<?> saveNewAddressByClientId(@Valid @RequestBody Address newAddress, BindingResult result, @PathVariable Long clientId) {
        // To handle of obligations of object attributes
        if (result.hasFieldErrors()) {
            return utilValidation.validation(result);
        }

        // Call the 'savePetByClient' method
        Optional<Client> optionalNewClient = service.saveAddressByClient(clientId, newAddress);

        // if the client is present then it means that the object could be saved
        if (optionalNewClient.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalNewClient.get());
        }

        // Else, return an empty optional
        return ResponseEntity.notFound().build();
    }
    
    // To create an endpoint that allows updating information of the address of a
    // certain client
    @PutMapping("/{clientId}/addresses")
    public ResponseEntity<?> editAddressByClientId(@Valid @RequestBody Address editAddress, BindingResult result, @PathVariable Long clientId) {
        // To handle of obligations of object attributes
        if (result.hasFieldErrors()) {
            return utilValidation.validation(result);
        }

        // Call the 'editPetByClient' method
        Optional<Client> optionalUpdateClient = service.editAddressByClient(clientId, editAddress);

        // if the client is present then it means that the object could be updated
        if ( optionalUpdateClient.isPresent() ) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUpdateClient.get());
        }

        // Else, return a 404 status code.
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows deleting a certain address of a certain client
    @DeleteMapping("/{clientId}/addresses")
    public ResponseEntity<?> deleteAddressByClientId(@PathVariable Long clientId) {

        // Call the 'deletePetByClient' method
        Optional<Client> optionalUpdateClient = service.deleteAddressByClient(clientId);

        // if the client is present then it means that the object could be deleted
        if ( optionalUpdateClient.isPresent() ) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalUpdateClient.get());
        }

        // Else, return an empty optional
        return ResponseEntity.notFound().build();
    }

}
