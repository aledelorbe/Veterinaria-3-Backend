package com.alejandro.veterinaria.services;

import java.util.List;
import java.util.Optional;

import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.entities.Pet;

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
    // Methods for pet entity
    // -----------------------------

    public Client savePetByClientId(Client clientDb, Pet newPet);

    public Client deletePetByClientId(Client clientDb, Pet petDb);
    
    public Client editPetByClientId(Client clientDb, Pet petDb, Pet editPet);
}
