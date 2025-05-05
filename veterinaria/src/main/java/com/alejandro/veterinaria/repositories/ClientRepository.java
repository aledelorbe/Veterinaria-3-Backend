package com.alejandro.veterinaria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.alejandro.veterinaria.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {

    // ---------------------
    // Custom queries ------
    // ---------------------

    // To search for a client by his name
    List<Client> findByNameContaining(String name);

    // To search for a client by his lastname
    List<Client> findByLastnameContaining(String lastname);

    // To get all of the clients whose pets have a certain name
    @Query("SELECT c FROM Client c JOIN c.pets p WHERE p.name LIKE %?1%")
    List<Client> findClientsByPetNameLike(String petName);

}
