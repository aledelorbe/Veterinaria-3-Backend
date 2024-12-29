package com.alejandro.veterinaria.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.veterinaria.entities.Address;
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
    // This method is a 'join point'
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
        // Search for a specific client
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

    // To update the information about the pet
    @Override
    @Transactional
    public Client editPetByClientId(Client clientDb, Pet petDb, Pet editPet) {

        // update all of object attributes
        petDb.setName(editPet.getName());
        petDb.setSpecie(editPet.getSpecie());
        petDb.setBreed(editPet.getBreed());
        petDb.setAge(editPet.getAge());
        petDb.setReasonForVisit(editPet.getReasonForVisit());

        return repository.save(clientDb);
    }

    // To delete a certain pet in the db
    @Override
    @Transactional
    public Client deletePetByClientId(Client clientDb, Pet petDb) {

        clientDb.getPets().remove(petDb);

        return repository.save(clientDb);
    }

    // -----------------------------
    // Methods for address entity
    // -----------------------------

    // To save a new address of a certain client in the db
    @Override
    @Transactional
    public Client saveAddressByClientId(Client clientDb, Address newAddress) {

        clientDb.setAddress(newAddress);

        return repository.save(clientDb);
    }

    // To update the information about the address
    @Override
    @Transactional
    public Client editAddressByClientId(Client clientDb, Address editAddress) {

        Address addressDb = clientDb.getAddress();

        // update all of object attributes
        addressDb.setStreet(editAddress.getStreet());
        addressDb.setState(editAddress.getState());
        addressDb.setCity(editAddress.getCity());
        addressDb.setCp(editAddress.getCp());

        return repository.save(clientDb);
    }

    // To delete a certain address in the db
    @Override
    @Transactional
    public Client deleteAddressByClientId(Client clientDb) {

        clientDb.setAddress(null);

        return repository.save(clientDb);
    }

    // -----------------------------
    // Methods for custom queries of client entity
    // -----------------------------

    // To get the address of certain client
    @Override
    @Transactional(readOnly = true)
    public Address getAddressByClientId(Long id_client) {
        return repository.getAddressByClientId(id_client);
    }
    
    // To get all the pets of certain client
    @Override
    @Transactional(readOnly = true)
    public List<Pet> getPetsByClientId(Long id_client) {
        return repository.getPetsByClientId(id_client);
    }

}
