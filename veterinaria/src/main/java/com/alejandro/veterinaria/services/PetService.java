package com.alejandro.veterinaria.services;

import java.util.List;
import java.util.Optional;

import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.entities.Pet;


public interface PetService {

    // Declaration of methods to use in 'serviceImp' file

    // -----------------------------
    // Methods for pet entity
    // -----------------------------

    public Optional<Pet> findById(Long id);
    
    public List<Pet> getPetsByClient(Client clientDb);

    public Client savePetByClient(Client clientDb, Pet newPet);
    
    public Optional<Client> editPetByClient(Client clientDb, Long petId, Pet editPet);
    
    public Optional<Client> deletePetByClient(Client clientDb, Long petId);

}
