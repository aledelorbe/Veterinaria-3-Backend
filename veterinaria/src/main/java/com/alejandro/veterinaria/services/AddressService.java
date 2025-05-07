package com.alejandro.veterinaria.services;

import java.util.Optional;

import com.alejandro.veterinaria.entities.Address;
import com.alejandro.veterinaria.entities.Client;

public interface AddressService {
    
    // Declaration of methods to use in 'serviceImp' file

    // -----------------------------
    // Methods for address entity
    // -----------------------------

    public Optional<Address> getAddressByClient(Client clientDb);

    public Client saveAddressByClient(Client clientDb, Address newAddress);

    public Client editAddressByClient(Client clientDb, Address editAddress);

    public Client deleteAddressByClient(Client clientDb);
    
}
