package com.alejandro.veterinaria.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.entities.Pet;
import com.alejandro.veterinaria.repositories.ClientRepository;
import com.alejandro.veterinaria.repositories.PetRepository;


@Service
public class PetServiceImp implements PetService {

    // To inject the repository dependency.
    @Autowired
    private ClientRepository clientRepository;

    // To inject the repository dependency.
    @Autowired
    private PetRepository repository;

    // -----------------------------
    // Methods for pet entity
    // -----------------------------

    // To get a specific pet based on its id
    @Override
    @Transactional(readOnly = true)
    public Optional<Pet> findById(Long id) {
        return repository.findById(id);
    }

    // To save a new pet of a certain client in the db
    @Override
    @Transactional
    public Optional<Client> savePetByClient(Long clientId, Pet newPet) {

        // Search for a specific client
        Optional<Client> optionalClient = clientRepository.findById(clientId);

        // if it exists then save the pet and return the new data (new client)
        if ( optionalClient.isPresent() ) {
            Client clientDb = optionalClient.get();

            clientDb.getPets().add(newPet);
    
            return Optional.of(clientRepository.save(clientDb));
        }

        // Else, return an empty optional
        return optionalClient;
    }

    // To update the information about the pet
    @Override
    @Transactional
    public Optional<Client> editPetByClient(Long clientId, Long petId, Pet editPet) {

        // Search for a specific client and specific pet
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Optional<Pet> optionalPet = repository.findById(petId);

        // If the client and pet are present then ...
        if ( optionalClient.isPresent() && optionalPet.isPresent() ) {
        
            // Check if the pet belongs to this client.
            Client clientDb = optionalClient.get();
            Optional<Pet> optionalBelongingPet = clientDb.getPets().stream().filter(many -> many.getId().equals(petId)).findFirst();

            // If this pet is present it means the client is owner of pet
            if (optionalBelongingPet.isPresent()) {
                // Update all of object attributes 
                Pet petDb = optionalBelongingPet.get();

                petDb.setName(editPet.getName());
                petDb.setSpecie(editPet.getSpecie());
                petDb.setBreed(editPet.getBreed());
                petDb.setAge(editPet.getAge());
                petDb.setReasonForVisit(editPet.getReasonForVisit());

                // and save the information in the db
                return Optional.of(clientRepository.save(clientDb));
            }

            // Else, return an empty optional
            return Optional.empty();
        }
        
        // Else, return an empty optional
        return Optional.empty();
    }

    // To delete a certain pet in the db
    @Override
    @Transactional
    public Optional<Client> deletePetByClient(Long clientId, Long petId) {

        // Search for a specific client and specific pet
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        Optional<Pet> optionalPet = repository.findById(petId);

        // If the client and pet are present then ...
        if ( optionalClient.isPresent() && optionalPet.isPresent() ) {
        
            // Search for the pet that will be deleted
            Client clientDb = optionalClient.get();
            Optional<Pet> optionalBelongingPet = clientDb.getPets().stream().filter(many -> many.getId().equals(petId)).findFirst();

            // If this pet is present it means the client is owner of pet
            if (optionalBelongingPet.isPresent()) {
                // Delete the pet
                clientDb.getPets().remove(optionalBelongingPet.get());

                // and save the information in the db
                return Optional.of(clientRepository.save(clientDb));
            }

            // Else, return an empty optional
            return Optional.empty();
        }
        
        // Else, return an empty optional
        return Optional.empty();
    }

}
