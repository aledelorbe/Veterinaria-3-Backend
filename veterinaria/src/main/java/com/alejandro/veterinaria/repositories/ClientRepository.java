package com.alejandro.veterinaria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.alejandro.veterinaria.entities.Address;
import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.entities.Pet;

public interface ClientRepository extends CrudRepository<Client, Long> {

    // ---------------------
    // Custom queries ------
    // ---------------------

    // To get the address of certain client
    @Query("""
            SELECT ad
            FROM Address ad
            JOIN Client cl
            ON cl.address.id = ad.id
            WHERE cl.id = ?1
            """)
    Address getAddressByClientId(Long id_client);

    // To get all the pets of certain client
    @Query("""
            SELECT cl.pets
            FROM Client cl 
            WHERE cl.id = ?1
            """)
    List<Pet> getPetsByClientId(Long id_client);

    // To search for a client by his name
    List<Client> findByNameContaining(String name);

    // To search for a client by his lastname
    List<Client> findByLastnameContaining(String lastname);

    // To get all of the clients whose pets have a certain name
    @Query("SELECT c FROM Client c JOIN c.pets p WHERE p.name LIKE %?1%")
    List<Client> findClientsByPetNameLike(String petName);

}
