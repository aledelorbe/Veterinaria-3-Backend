package com.alejandro.veterinaria.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.entities.Pet;
import com.alejandro.veterinaria.repositories.ClientRepository;

@Service
public class ClientServiceImp implements ClientService {

    // To inject the repository dependency.
    @Autowired
    private ClientRepository repository;

    // -----------------------------
    // Methods for client entity
    // -----------------------------

    // To list all of clients (records) in the table 'clients'
    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return (List<Client>) repository.findAll(); // cast because the method findAll returns a iterable.
    }

    // To get a specific client based on its id
    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findById(Long id) {
        return repository.findById(id);
    }

    // To save a new client in the db
    @Override
    @Transactional
    public Client save(Client client) {
        return repository.save(client);
    }

    // To update a specific client based on its id
    @Override
    @Transactional
    public Optional<Client> update(Long id, Client client) {
        // Find a specific client
        Optional<Client> optionalClient = repository.findById(id);

        // If the client is present then...
        if (optionalClient.isPresent()) {
            // update that record and return an optional value
            Client clientDb = optionalClient.get();

            clientDb.setName(client.getName());
            clientDb.setLastname(client.getLastname());
            clientDb.setEmail(client.getEmail());
            clientDb.setPhonenumber(client.getPhonenumber());

            return Optional.ofNullable(repository.save(clientDb));
        }

        return optionalClient;
    }

    // To delete a specific client based on its id
    @Override
    @Transactional
    public Optional<Client> deleteById(Long id) {
        // Search a specific client
        Optional<Client> optionalClient = repository.findById(id);

        // If the client is present then delete that client
        optionalClient.ifPresent(clientDb -> {
            repository.deleteById(id);
        });

        return optionalClient;
    }

    // -----------------------------
    // Methods for pet entity
    // -----------------------------

    // To save a new pet of a certain client in the db
    @Override
    @Transactional
    public Client savePetByClientId(Client clientDb, Pet newPet) {
        
        clientDb.getPets().add(newPet);
        
        return repository.save(clientDb);
    }
    
    // To delete a certain pet of a certain client in the db
    @Override
    @Transactional
    public Client deletePetByClientId(Client clientDb, Pet petDb) {

        clientDb.getPets().remove(petDb);

        return repository.save(clientDb);
    }




    @Override
    @Transactional
    public Client editPetByClientId(Client clientDb, Pet petDb, Pet editPet) {

        petDb.setName(editPet.getName());
        petDb.setSpecie(editPet.getSpecie());
        petDb.setBreed(editPet.getBreed());
        petDb.setAge(editPet.getAge());

        return repository.save(clientDb);
    }

}
