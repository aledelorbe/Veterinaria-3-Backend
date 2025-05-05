package com.alejandro.veterinaria.services;

import java.util.List;
import java.util.Optional;

import com.alejandro.veterinaria.entities.Client;

public interface ClientService {

    // Declaration of methods to use in 'serviceImp' file

    // -----------------------------
    // Methods for client entity
    // -----------------------------

    public List<Client> findAll();

    public Optional<Client> findById(Long id);
    
    public Client save(Client client);
    
    public Optional<Client> update(Long id, Client client);
    
    public Optional<Client> deleteById(Long id);

    // -----------------------------
    // Methods for custom queries of client entity
    // -----------------------------

    public List<Client> findByNameContaining(String name);

    public List<Client> findByLastnameContaining(String lastname);

    public List<Client> findClientsByPetNameLike(String petName);

}
