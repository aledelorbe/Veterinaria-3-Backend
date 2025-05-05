package com.alejandro.veterinaria.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.veterinaria.entities.Address;
import com.alejandro.veterinaria.entities.Client;
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
    // Methods for address entity
    // -----------------------------

    // To get the address of certain client
    @Override
    @Transactional(readOnly = true)
    public Optional<Address> getAddressByClientId(Client clientDb) {
        return Optional.ofNullable(clientDb.getAddress());
    }

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

    // To get all of the clients with a certain name
    @Override
    @Transactional(readOnly = true)
    public List<Client> findByNameContaining(String name) {
        return repository.findByNameContaining(name);
    }

    // To get all of the clients with a certain lastname
    @Override
    @Transactional(readOnly = true)
    public List<Client> findByLastnameContaining(String lastname) {
        return repository.findByLastnameContaining(lastname);
    }
    
    // To get all of the clients whose pets have a certain name
    @Override
    @Transactional(readOnly = true)
    public List<Client> findClientsByPetNameLike(String petName) {
        return repository.findClientsByPetNameLike(petName);
    }

}
