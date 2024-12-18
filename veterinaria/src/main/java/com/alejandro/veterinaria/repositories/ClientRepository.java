package com.alejandro.veterinaria.repositories;

import org.springframework.data.repository.CrudRepository;

import com.alejandro.veterinaria.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
    
    public boolean existsByNameAndLastname(String name, String lastname);
}
