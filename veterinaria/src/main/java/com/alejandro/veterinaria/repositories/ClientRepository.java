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

}
