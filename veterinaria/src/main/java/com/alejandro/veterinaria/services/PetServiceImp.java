package com.alejandro.veterinaria.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.veterinaria.entities.Pet;
import com.alejandro.veterinaria.repositories.PetRepository;


@Service
public class PetServiceImp implements PetService {

    // To inject the repository dependency.
    @Autowired
    private PetRepository repository;

    // To get a specific pet based on its id
    @Override
    @Transactional
    public Optional<Pet> findById(Long id) {
        return repository.findById(id);
    }

    // To update a certain pet of a certain client in the db
    @Override
    @Transactional
    public Pet updatePet(Pet petDb, Pet editPet) {

        petDb.setName(editPet.getName());
        petDb.setSpecie(editPet.getSpecie());
        petDb.setBreed(editPet.getBreed());
        petDb.setAge(editPet.getAge());

        return repository.save(petDb);
    }
}
