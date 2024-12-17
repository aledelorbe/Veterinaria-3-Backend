package com.alejandro.veterinaria.services;

import java.util.Optional;

import com.alejandro.veterinaria.entities.Pet;


public interface PetService {

    // Declaration of methods to use in 'serviceImp' file

    public Optional<Pet> findById(Long id);

    public Pet updatePet(Pet petDb, Pet editPet);

}
