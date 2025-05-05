package com.alejandro.veterinaria.services;

import java.util.Optional;

import com.alejandro.veterinaria.entities.Address;
import com.alejandro.veterinaria.entities.Client;

public interface AddressService {
    
    // Declaration of methods to use in 'serviceImp' file

    // -----------------------------
    // Methods for address entity
    // -----------------------------

    public Optional<Address> getAddressByClientId(Client clientDb);

    public Client saveAddressByClientId(Client clientDb, Address newAddress);

    public Client editAddressByClientId(Client clientDb, Address editAddress);

    public Client deleteAddressByClientId(Client clientDb);
    
}
