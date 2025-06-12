package com.alejandro.veterinaria.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.veterinaria.entities.Address;
import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.repositories.ClientRepository;


@Service
public class AddressServiceImp implements AddressService {
    
    // To inject the repository dependency.
    @Autowired
    private ClientRepository clientRepository;

    // -----------------------------
    // Methods for address entity
    // -----------------------------

    // To save a new address of a certain client in the db
    @Override
    @Transactional
    public Optional<Client> saveAddressByClient(Long clientId, Address newAddress) {

        // Search for a specific client
        Optional<Client> optionalClient = clientRepository.findById(clientId);

        // if it exists then save the address and return the new data (new client)
        if ( optionalClient.isPresent() ) {
            Client clientDb = optionalClient.get();

            clientDb.setAddress(newAddress);
    
            return Optional.of(clientRepository.save(clientDb));
        }

        // Else, return an empty optional
        return optionalClient;
    }

    // To update the information about the address
    @Override
    @Transactional
    public Optional<Client> editAddressByClient(Long clientId, Address editAddress) {

        // Search for a specific client
        Optional<Client> optionalClient = clientRepository.findById(clientId);

        // If the client is present then ...
        if ( optionalClient.isPresent() ) {
        
            Client clientDb = optionalClient.get();

            // If the client has an address then...
            if ( clientDb.getAddress() != null ) {
                
                // Edit the address belongs to this client.
                Address addressDb = clientDb.getAddress();
        
                // update all of object attributes
                addressDb.setStreet(editAddress.getStreet());
                addressDb.setState(editAddress.getState());
                addressDb.setCity(editAddress.getCity());
                addressDb.setCp(editAddress.getCp());
        
                // and save the information in the db
                return Optional.of(clientRepository.save(clientDb));
            }

            // Else, return an empty optional
            return Optional.empty();
        }
        
        // Else, return an empty optional
        return Optional.empty();
    }

    // To delete a certain address in the db
    @Override
    @Transactional
    public Optional<Client> deleteAddressByClient(Long clientId) {

        // Search for a specific client
        Optional<Client> optionalClient = clientRepository.findById(clientId);

        // If the client is present then ...
        if ( optionalClient.isPresent() ) {
        
            Client clientDb = optionalClient.get();
        
            // Delete the address
            clientDb.setAddress(null);
    
            // and save the information in the db
            return Optional.of(clientRepository.save(clientDb));
        }

        // Else, return an empty optional
        return Optional.empty();
    }

}
