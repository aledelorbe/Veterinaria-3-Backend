package com.alejandro.veterinaria.services;

import java.util.List;
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

    // To get all the pets of certain client
    @Override
    @Transactional(readOnly = true)
    public List<Pet> getPetsByClient(Client clientDb) {
        return clientDb.getPets();
    }

    // To save a new pet of a certain client in the db
    @Override
    @Transactional
    public Client savePetByClientId(Client clientDb, Pet newPet) {

        clientDb.getPets().add(newPet);

        return clientRepository.save(clientDb);
    }

    // To update the information about the pet
    @Override
    @Transactional
    public Optional<Client> editPetByClientId(Client clientDb, Long petId, Pet editPet) {

        // Search for the pet that will be updated
        Optional<Pet> optionalPet = clientDb.getPets().stream().filter(many -> many.getId().equals(petId)).findFirst();

        // If this pet is present it means the client is owner of pet
        if (optionalPet.isPresent()) {
            // Update all of object attributes 
            Pet petDb = optionalPet.get();

            petDb.setName(editPet.getName());
            petDb.setSpecie(editPet.getSpecie());
            petDb.setBreed(editPet.getBreed());
            petDb.setAge(editPet.getAge());
            petDb.setReasonForVisit(editPet.getReasonForVisit());

            // and save the information in the db
            return Optional.of(clientRepository.save(clientDb));
        }

        return Optional.empty();
    }

    // To delete a certain pet in the db
    @Override
    @Transactional
    public Optional<Client> deletePetByClientId(Client clientDb, Long petId) {

        // Search for the pet that will be deleted
        Optional<Pet> optionalPet = clientDb.getPets().stream().filter(many -> many.getId().equals(petId)).findFirst();

        // If this pet is present it means the client is owner of pet
        if (optionalPet.isPresent()) {
            // Delete the pet
            clientDb.getPets().remove(optionalPet.get());

            // and save the information in the db
            return Optional.of(clientRepository.save(clientDb));
        }

        return Optional.empty();
    }

}
