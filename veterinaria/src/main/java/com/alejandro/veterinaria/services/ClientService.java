package com.alejandro.veterinaria.services;

import java.util.List;
import java.util.Optional;

import com.alejandro.veterinaria.entities.Address;
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
    
    public Client editPetByClientId(Client clientDb, Pet petDb, Pet editPet);
    
    public Client deletePetByClientId(Client clientDb, Pet petDb);
    
    // -----------------------------
    // Methods for address entity
    // -----------------------------

    public Optional<Address> getAddressByClientId(Client clientDb);

    public Client saveAddressByClientId(Client clientDb, Address newAddress);

    public Client editAddressByClientId(Client clientDb, Address editAddress);

    public Client deleteAddressByClientId(Client clientDb);

    // -----------------------------
    // Methods for custom queries of client entity
    // -----------------------------

    public List<Pet> getPetsByClientId(Long id_client);
    
    public List<Client> findByNameContaining(String name);

    public List<Client> findByLastnameContaining(String lastname);

    public List<Client> findClientsByPetNameLike(String petName);

}
