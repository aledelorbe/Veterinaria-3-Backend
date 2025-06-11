package com.alejandro.veterinaria.services;

import java.util.Optional;

import com.alejandro.veterinaria.entities.Address;
import com.alejandro.veterinaria.entities.Client;

public interface AddressService {
    
    // Declaration of methods to use in 'serviceImp' file

    // -----------------------------
    // Methods for address entity
    // -----------------------------

    public Optional<Client> saveAddressByClient(Long clientId, Address newAddress);

    public Optional<Client> editAddressByClient(Long clientId, Address editAddress);

    public Optional<Client> deleteAddressByClient(Long clientId);
    
}
