package com.alejandro.veterinaria.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alejandro.veterinaria.data.PetData;
import com.alejandro.veterinaria.data.ClientData;
import com.alejandro.veterinaria.data.CustomCondition;
import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.entities.Pet;
import com.alejandro.veterinaria.repositories.ClientRepository;
import com.alejandro.veterinaria.repositories.PetRepository;

@ExtendWith(MockitoExtension.class)
class PetServiceImpTest {
    
    // To create a mock
    @Mock
    PetRepository repository; 

    // To create a mock
    @Mock
    ClientRepository clientRepository; 

    // To create a service object with the injection of a mock
    @InjectMocks
    PetServiceImp service;

    // To create a service object with the injection of a mock
    @InjectMocks
    ClientServiceImp clientService;

    // To test the metod findById when we use an existing id
    @Test
    void findByIdExistingIdTest() {

        // Given
        Long idToSearch = 50L;
        when(repository.findById(anyLong())).thenReturn(Optional.of(PetData.createPet005()));

        // when
        Optional<Pet> optionalPet = service.findById(idToSearch);

        // then
        assertNotNull(optionalPet.get());
        assertEquals(50L, optionalPet.get().getId());
        assertEquals("nala", optionalPet.get().getName());
        assertEquals("perro", optionalPet.get().getSpecie());
        assertNull(optionalPet.get().getBreed());
        assertEquals(4L, optionalPet.get().getAge());
        assertEquals("no quiere comer", optionalPet.get().getReasonForVisit());
        
        verify(repository).findById(argThat(new CustomCondition(PetData.idsValid, true)));
    }

    // To test the metod findById when we use an inexisting id
    @Test
    void findByIdInexistingIdTest() {

        // Given
        Long idToSearch = 11L;
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        Optional<Pet> optionalPet2 = service.findById(idToSearch);

        // then
        assertFalse(optionalPet2.isPresent());
        assertThrows(NoSuchElementException.class, () -> {
            optionalPet2.orElseThrow();
        });

        verify(repository).findById(argThat(new CustomCondition(PetData.idsValid, false)));
    }

    // To test the metod getPetsByClientId
    @Test
    void getPetsByClientIdTest() {
        
        // Given
        Long idToSearch = 4L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient004()));

        // when
        Optional<Client> optionalClient = clientService.findById(idToSearch);
        List<Pet> pets = service.getPetsByClient(optionalClient.get());

        // then
        assertNotNull(pets);
        assertEquals(2, pets.size());
        assertEquals(80L, pets.get(1).getId());
        assertEquals("quick", pets.get(1).getName());
        assertEquals("perro", pets.get(1).getSpecie());
        assertEquals("labrador", pets.get(1).getBreed());
        assertEquals(10L, pets.get(1).getAge());
        assertEquals("tiene mucho sueño", pets.get(1).getReasonForVisit());

        verify(clientRepository).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
    }

    // To test the metod savePetByClientId
    @Test
    void savePetByClientIdTest() {
    
        // Given
        Client clientDb = ClientData.createClient004();
        Pet petInsert = new Pet(null, "lince intergalactico", "lince", null, 5L, "le duele el estomago");
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // when
        Client newClient = service.savePetByClientId(clientDb, petInsert);
        
        // then
        assertEquals("Esteban", newClient.getName());
        assertEquals("Gonzalez", newClient.getLastname());
        assertEquals("pastor34@idoidraw.com", newClient.getEmail());
        assertEquals(1234567890L, newClient.getPhonenumber());

        int size = newClient.getPets().size();
        List<Pet> pets = newClient.getPets();

        assertEquals("lince intergalactico", pets.get(size - 1).getName());
        assertEquals("lince", pets.get(size - 1).getSpecie());
        assertNull(pets.get(size - 1).getBreed());
        assertEquals(5L, pets.get(size - 1).getAge());
        assertEquals("le duele el estomago", pets.get(size - 1).getReasonForVisit());

        verify(clientRepository).save(any(Client.class));
    }

    // To test the metod editPetByClientId when we use an existing id
    @Test
    void editPetByClientIdExistingIdTest() {
        
        // Given
        Long idToSearch = 80L;
        Client clientDb = ClientData.createClient004();
        Pet petToUpdate = new Pet(null, "lince update", "reptil", null, 3L, "infeccion en los ojos");
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Optional<Client> optionalClient = service.editPetByClientId(clientDb, idToSearch, petToUpdate);

        // then
        assertEquals("Esteban", optionalClient.get().getName());
        assertEquals("Gonzalez", optionalClient.get().getLastname());
        assertEquals("pastor34@idoidraw.com", optionalClient.get().getEmail());
        assertEquals(1234567890L, optionalClient.get().getPhonenumber());

        Pet petUpdated = optionalClient.get().getPets().get(1);

        assertEquals("lince update", petUpdated.getName());
        assertEquals("reptil", petUpdated.getSpecie());
        assertNull(petUpdated.getBreed());
        assertEquals(3L, petUpdated.getAge());
        assertEquals("infeccion en los ojos", petUpdated.getReasonForVisit());

        verify(clientRepository).save(any(Client.class));
    }

    // To test the metod editPetByClientId when we use an inexisting id
    @Test
    void editPetByClientIdInexistingIdTest() {
        
        // Given
        Long idToSearch = 9999L;
        Client clientDb = ClientData.createClient004();
        Pet petToUpdate = new Pet(null, "lince update", "reptil", null, 3L, "infeccion en los ojos");
        
        // When
        Optional<Client> optionalClient = service.editPetByClientId(clientDb, idToSearch, petToUpdate);

        // Then
        assertFalse(optionalClient.isPresent());
        assertThrows(NoSuchElementException.class, () -> {
            optionalClient.orElseThrow();
        });

        verify(clientRepository, never()).save(any(Client.class));
    }

    // To test the metod deletePetByClientId when we use an existing id
    @Test
    void deletePetByClientIdExistingIdTest() {
        
        // Given
        Long clientIdToSearch = 4L;
        Long petIdToSearch = 80L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient004()));
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Optional<Client> optionalClient = clientService.findById(clientIdToSearch);
        List<Pet> pets = service.getPetsByClient(optionalClient.get());

        // then
        assertNotNull(pets);
        assertEquals(2, pets.size());

        // when
        Optional<Client> optionalClient2 = service.deletePetByClientId(optionalClient.get(), petIdToSearch);
        List<Pet> pets2 = service.getPetsByClient(optionalClient2.get());

        // then
        assertNotNull(pets2);
        assertEquals(1, pets2.size());

        verify(clientRepository).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(clientRepository).save(any(Client.class));
    }

    // To test the metod deletePetByClientId when we use an inexisting id
    @Test
    void deletePetByClientIdInexistingIdTest() {
        
        // Given
        Long clientIdToSearch = 4L;
        Long petIdToSearch = 999999L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient004()));

        // when: get pets (first time)
        Optional<Client> optionalClient = clientService.findById(clientIdToSearch);
        List<Pet> pets = service.getPetsByClient(optionalClient.get());

        // then
        assertNotNull(pets);
        assertEquals(2, pets.size());

        // when: delete
        Optional<Client> optionalClient2 = service.deletePetByClientId(optionalClient.get(), petIdToSearch);

        // then
        assertFalse(optionalClient2.isPresent());

        // when: get pets (Second time)
        Optional<Client> optionalClient3 = clientService.findById(clientIdToSearch);
        List<Pet> pets2 = service.getPetsByClient(optionalClient3.get());

        // then
        assertNotNull(pets2);
        assertEquals(2, pets2.size());

        verify(clientRepository, times(2)).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(clientRepository, never()).save(any(Client.class));
    }

}
