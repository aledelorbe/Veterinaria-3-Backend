package com.alejandro.veterinaria.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.alejandro.veterinaria.entities.Address;
import com.alejandro.veterinaria.entities.Client;

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
    Address getAddressesByClientId(Long id_client);



}
