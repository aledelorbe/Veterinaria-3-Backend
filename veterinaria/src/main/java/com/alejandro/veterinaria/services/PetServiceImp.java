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
    @Transactional(readOnly = true)
    public Optional<Pet> findById(Long id) {
        return repository.findById(id);
    }
}
