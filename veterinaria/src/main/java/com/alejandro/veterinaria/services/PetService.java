package com.alejandro.veterinaria.services;

import java.util.Optional;

import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.entities.Pet;


public interface PetService {

    // Declaration of methods to use in 'serviceImp' file

    // -----------------------------
    // Methods for pet entity
    // -----------------------------

    public Optional<Pet> findById(Long id);
    
    public Optional<Client> savePetByClient(Long clientId, Pet newPet);
    
    public Optional<Client> editPetByClient(Long clientId, Long petId, Pet editPet);
    
    public Optional<Client> deletePetByClient(Long clientId, Long petId);

}
