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

    // To create an endpoint that allows invoking the method 'getAddressByClient'.
    @GetMapping("/{id_client}/addresses")
    public ResponseEntity<?> getAddressByClient(@PathVariable Long id_client) {
        // Search for a specific client and if it's present then return it.
        Optional<Client> optionalClient = clientService.findById(id_client);

        if (optionalClient.isPresent()) {

            Optional<Address> optionalAddress = service.getAddressByClient(optionalClient.get());

            // If the address is present the return it.
            if (optionalAddress.isPresent()) {
                return ResponseEntity.ok(optionalAddress.get());
            }

            // Else returns code response 204 and a void body
            return ResponseEntity.noContent().build();
        }

        // Else returns code response 404
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows saving a new address of an certain client
    @PostMapping("/{clientId}/addresses")
    public ResponseEntity<?> saveNewAddressByClientId(@Valid @RequestBody Address newAddress, BindingResult result, @PathVariable Long clientId) {
        // To handle of obligations of object attributes
        if (result.hasFieldErrors()) {
            return utilValidation.validation(result);
        }

        // Search for a specific client if it exists then save the address
        Optional<Client> optionalClient = clientService.findById(clientId);

        if (optionalClient.isPresent()) {
            Client newClient = service.saveAddressByClient(optionalClient.get(), newAddress);
            return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
        }
        // Else returns code response 404
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

        // Search for a specific client if it is present then edit the information about address
        Optional<Client> optionalClient = clientService.findById(clientId);

        if (optionalClient.isPresent()) {
            Client updateClient = service.editAddressByClient(optionalClient.get(), editAddress);
            return ResponseEntity.status(HttpStatus.CREATED).body(updateClient);
        }
        // Else returns code response 404
        return ResponseEntity.notFound().build();
    }

    // To create an endpoint that allows deleting a certain address of a certain client
    @DeleteMapping("/{clientId}/addresses")
    public ResponseEntity<?> deleteAddressByClientId(@PathVariable Long clientId) {

        // Search for a specific client if it is present then delete a address
        Optional<Client> optionalClient = clientService.findById(clientId);

        if (optionalClient.isPresent()) {
            Client updateClient = service.deleteAddressByClient(optionalClient.get());
            return ResponseEntity.ok(updateClient);
        }
        // Else returns code response 404
        return ResponseEntity.notFound().build();
    }

}
