package com.alejandro.veterinaria.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.veterinaria.entities.Address;
import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.repositories.ClientRepository;

public class AddressServiceImp implements AddressService {
    
    // To inject the repository dependency.
    @Autowired
    private ClientRepository repository;

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

}
