package com.alejandro.veterinaria.services;

import com.alejandro.veterinaria.entities.Client;

// import java.util.List;
// import java.util.Optional;

import com.alejandro.veterinaria.entities.Pet;

public interface PetService {

    // Declaration of methods to use in 'serviceImp' file
    
    // public List<Pet> findAllOfPetsByClientId(Long clientId);

    public void savePetByClientId(Client clientDb, Pet newPet);

    // public Optional<Pet> updatePetByClientId(Long clientId, Long petId, Pet pet);

    // public Optional<Pet> deletePetByClientId(Long clientId, Long petId);
}
