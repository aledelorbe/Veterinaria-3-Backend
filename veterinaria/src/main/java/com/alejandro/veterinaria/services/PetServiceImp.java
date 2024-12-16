package com.alejandro.veterinaria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.entities.Pet;
import com.alejandro.veterinaria.repositories.ClientRepository;

@Service
public class PetServiceImp implements PetService{

    // To inject the repository dependency.
    // @Autowired
    // private PetRepository repository;

    // To inject the repository dependency.
    @Autowired
    private ClientRepository clientRepository;

    // // To get all of the pets of a certain client
    // @Override
    // @Transactional(readOnly = true)
    // public List<Pet> findAllOfPetsByClientId(Long clientId) {
    //     return repository.findByClient_id(clientId);
    // }

    // To save a new pet in the db
    @Override
    @Transactional
    public void savePetByClientId(Client clientDb, Pet newPet) {

        // clientDb.setPets(new HashSet<>(Set.of(newPet)));
        // clientDb.setPets(Set.of(newPet));
        clientDb.getPets().add(newPet);

        clientRepository.save(clientDb);
    }

    // // To update a specific pet based on its id
    // @Override
    // @Transactional
    // public Optional<Pet> updatePetByClientId(Long clientId, Long petId, Pet pet) {
    //     // Find a specific pet
    //     Optional<Pet> optionalPet = repository.findById(id);

    //     // If the pet is present then...
    //     if (optionalPet.isPresent()) {
    //         // update that record and return an optional value
    //         Pet petDb = optionalPet.get();

    //         petDb.setName(pet.getName());
    //         petDb.setSpecie(pet.getSpecie());
    //         petDb.setAge(pet.getAge());

    //         petDb.setBreed(pet.getBreed() != null ? pet.getBreed() : "Sin raza");

    //         return Optional.ofNullable(repository.save(petDb));
    //     }

    //     return optionalPet;
    // }

    // // To delete a specific pet based on its id
    // @Override
    // @Transactional
    // public Optional<Pet> deletePetByClientId(Long clientId, Long petId) {
    //     // Search a specific pet
    //     Optional<Pet> optionalPet = repository.findById(id);

    //     // If the pet is present then delete that pet
    //     optionalPet.ifPresent(petDb -> {
    //         repository.deleteById(id);
    //     });

    //     return optionalPet;
    // }
}
