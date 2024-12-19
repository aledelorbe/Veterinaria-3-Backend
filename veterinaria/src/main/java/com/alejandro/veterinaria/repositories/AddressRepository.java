package com.alejandro.veterinaria.repositories;

import org.springframework.data.repository.CrudRepository;

import com.alejandro.veterinaria.entities.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
    
}
