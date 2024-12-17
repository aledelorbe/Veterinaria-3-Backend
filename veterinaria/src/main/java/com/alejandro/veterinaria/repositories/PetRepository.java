package com.alejandro.veterinaria.repositories;

import org.springframework.data.repository.CrudRepository;

import com.alejandro.veterinaria.entities.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {
    
}

