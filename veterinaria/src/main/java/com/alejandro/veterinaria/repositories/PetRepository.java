package com.alejandro.veterinaria.repositories;

// import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.alejandro.veterinaria.entities.Pet;

public interface PetRepository extends CrudRepository<Pet, Long>{

    // public List<Pet> findByClient_id(Long client_id);
}
