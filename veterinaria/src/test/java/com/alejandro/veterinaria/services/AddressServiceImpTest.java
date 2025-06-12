package com.alejandro.veterinaria.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.alejandro.veterinaria.data.ClientData;
import com.alejandro.veterinaria.data.CustomCondition;
import com.alejandro.veterinaria.entities.Address;
import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.repositories.ClientRepository;

@ExtendWith(MockitoExtension.class)
class AddressServiceImpTest {
    
    // To create a mock
    @Mock
    ClientRepository clientRepository; 

    // To create a service object with the injection of a mock
    @InjectMocks
    AddressServiceImp service;

    // To create a service object with the injection of a mock
    @InjectMocks
    ClientServiceImp clientService;

    // To test the 'saveAddressByClient' method when we use an existing client id
    @Test
    void saveAddressByClientExistingIdTest() {
    
        // Given
        Long idToSearch = 5L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient005()));
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Address addressInsert = new Address(null, "gomez farias", "chilpancingo", "guerrero", 56126L);
        
        // when
        Optional<Client> optionalNewClient = service.saveAddressByClient(idToSearch, addressInsert);
        Client newClient = optionalNewClient.get();
        Address newAddress = newClient.getAddress();
        
        // then
        assertEquals("John", newClient.getName());
        assertEquals("Lennon", newClient.getLastname());
        assertEquals("lennon@idoidraw.com", newClient.getEmail());
        assertEquals(45208954L, newClient.getPhonenumber());

        assertEquals("gomez farias", newAddress.getStreet());
        assertEquals("chilpancingo", newAddress.getState());
        assertEquals("guerrero", newAddress.getCity());
        assertEquals(56126L, newAddress.getCp());

        verify(clientRepository).save(any(Client.class));
    }

    // To test the 'saveAddressByClient' method when we use an inexisting client id
    @Test
    void saveAddressByClientInexistingIdTest() {
    
        // Given
        Long idToSearch = 99999L;
        Address addressInsert = new Address(null, "gomez farias", "chilpancingo", "guerrero", 56126L);
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        Optional<Client> optionalNewClient = service.saveAddressByClient(idToSearch, addressInsert);
        
        // Then
        assertFalse(optionalNewClient.isPresent());
        assertThrows(NoSuchElementException.class, () -> {
            optionalNewClient.orElseThrow();
        });

        verify(clientRepository).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
        verify(clientRepository, never()).save(any(Client.class));
    }

    // To test the 'editAddressByClient' method when an existent client ID is used 
    @Test
    void editAddressByClientExistingIdTest() {
        
        // Given
        Long idToSearch = 4L;
        Address addressToUpdate = new Address(null, "miguel hidalgo", "acatlan", "puebla", 56346L);
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient004()));
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Optional<Client> optionalNewClient = service.editAddressByClient(idToSearch, addressToUpdate);
        Client newClient = optionalNewClient.get();
        Address addressUpdated = newClient.getAddress();

        // then
        assertNotNull(newClient);
        assertEquals(4L, newClient.getId());
        assertEquals("Esteban", newClient.getName());
        assertEquals("Gonzalez", newClient.getLastname());
        assertEquals("pastor34@idoidraw.com", newClient.getEmail());
        assertEquals(1234567890L, newClient.getPhonenumber());

        assertEquals("miguel hidalgo", addressUpdated.getStreet());
        assertEquals("acatlan", addressUpdated.getState());
        assertEquals("puebla", addressUpdated.getCity());
        assertEquals(56346L, addressUpdated.getCp());

        verify(clientRepository).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(clientRepository).save(any(Client.class));
    }

    // To test the 'editAddressByClient' method when an existent client ID is used but the client doesnt have an address
    @Test
    void editAddressByClientExistingIdNoAddressTest() {
        
        // Given
        Long idToSearch = 5L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient005()));
        Address addressToUpdate = new Address(null, "miguel hidalgo", "acatlan", "puebla", 56346L);

        // When
        Optional<Client> optionalClient = service.editAddressByClient(idToSearch, addressToUpdate);

        // Then
        assertFalse(optionalClient.isPresent());
        assertThrows(NoSuchElementException.class, () -> {
            optionalClient.orElseThrow();
        });

        verify(clientRepository).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(clientRepository, never()).save(any(Client.class));
    }

    // To test the 'editAddressByClient' method when a non-existent client ID is used 
    @Test
    void editAddressByClientInexistingIdTest() {
        
        // Given
        Long idToSearch = 9999L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());
        Address addressToUpdate = new Address(null, "miguel hidalgo", "acatlan", "puebla", 56346L);

        // When
        Optional<Client> optionalClient = service.editAddressByClient(idToSearch, addressToUpdate);

        // Then
        assertFalse(optionalClient.isPresent());
        assertThrows(NoSuchElementException.class, () -> {
            optionalClient.orElseThrow();
        });

        verify(clientRepository).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
        verify(clientRepository, never()).save(any(Client.class));
    }

    // To test the 'deleteAddressByClient' method when an existent client ID is used 
    @Test
    void deleteAddressByClientTest() {
    
        // Given
        Long idToSearch = 3L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient003()));
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Optional<Client> optionalClient = service.deleteAddressByClient(idToSearch);
        Client newClient = optionalClient.get();

        // then
        assertNotNull(newClient);
        assertEquals(3L, newClient.getId());
        assertEquals("Celia", newClient.getName());
        assertEquals("Bello", newClient.getLastname());
        assertEquals("cazador19@idoidraw.com", newClient.getEmail());
        assertEquals(1234977026L, newClient.getPhonenumber());

        assertNull(newClient.getAddress());

        verify(clientRepository).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(clientRepository).save(any(Client.class));
    }

    // To test the 'deleteAddressByClient' method when a non-existent client ID is used 
    @Test
    void deleteAddressByClientInexistingIdTest() {
    
        // Given
        Long idToSearch = 999999L;
        when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Optional<Client> optionalClient = service.deleteAddressByClient(idToSearch);

        // Then
        assertFalse(optionalClient.isPresent());
        assertThrows(NoSuchElementException.class, () -> {
            optionalClient.orElseThrow();
        });

        verify(clientRepository).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
        verify(clientRepository, never()).save(any(Client.class));
    }

}
