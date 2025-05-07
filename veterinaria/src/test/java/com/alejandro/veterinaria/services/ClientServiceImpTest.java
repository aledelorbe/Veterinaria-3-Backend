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

import com.alejandro.veterinaria.data.ClientData;
import com.alejandro.veterinaria.data.CustomCondition;
import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.repositories.ClientRepository;

@ExtendWith(MockitoExtension.class)
class ClientServiceImpTest {
    
    // To create a mock
    @Mock
    ClientRepository repository; 

    // To create a service object with the injection of a mock
    @InjectMocks
    ClientServiceImp service;

    // To test the method findAll
    @Test
    void findAllTest() {

        // Given
        when(repository.findAll()).thenReturn(ClientData.createClients001());

        // when
        List<Client> clients = service.findAll();

        // then
        assertNotNull(clients);
        assertEquals(4, clients.size());
        assertEquals(2L, clients.get(1).getId());
        assertEquals("Hueto", clients.get(1).getName());
        assertEquals("Navejas", clients.get(1).getLastname());
        assertEquals("hekevim148@idoidraw.com", clients.get(1).getEmail());
        assertEquals(1538971230L, clients.get(1).getPhonenumber());

        verify(repository).findAll();
    }

    // To test the method findById when we use an existing id
    @Test
    void findByIdExistingIdTest() {

        // Given
        when(repository.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient004()));

        // when
        Optional<Client> optionalClient = service.findById(4L);

        // then
        assertNotNull(optionalClient.get());
        assertEquals(4L, optionalClient.get().getId());
        assertEquals("Esteban", optionalClient.get().getName());
        assertEquals("Gonzalez", optionalClient.get().getLastname());
        assertEquals("pastor34@idoidraw.com", optionalClient.get().getEmail());
        assertEquals(1234567890L, optionalClient.get().getPhonenumber());
        
        verify(repository).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
    }

    // To test the method findById when we use an inexisting id
    @Test
    void findByIdInexistingIdTest() {

        // Given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        Optional<Client> optionalClient2 = service.findById(11L);

        // then
        assertFalse(optionalClient2.isPresent());
        assertThrows(NoSuchElementException.class, () -> {
            optionalClient2.orElseThrow();
        });

        verify(repository).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
    }

    // To test the method save
    @Test
    void saveTest() {

        // Given
        Client clientInsert = new Client(null, "Javier", "Mejia", "enjambre@idoidraw.com", 5550374984L, null, null);
        when(repository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // when
        Client newClient = service.save(clientInsert);
        
        // then
        assertEquals("Javier", newClient.getName());
        assertEquals("Mejia", newClient.getLastname());
        assertEquals("enjambre@idoidraw.com", newClient.getEmail());
        assertEquals(5550374984L, newClient.getPhonenumber());

        verify(repository).save(any(Client.class));
    }

    // To test the method update when we use an existing id
    @Test
    void updateExistingIdTest() {

        // Given
        Long idToUpdate = 1L;
        Client clientToUpdate = new Client(idToUpdate, "Angel", "Sanchez", "daltonico@idoidraw.com", 5526384734L, null, null);
        when(repository.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient001()));
        when(repository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Optional<Client> result = service.update(idToUpdate, clientToUpdate);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Angel", result.get().getName());
        assertEquals("Sanchez", result.get().getLastname());
        assertEquals("daltonico@idoidraw.com", result.get().getEmail());
        assertEquals(5526384734L, result.get().getPhonenumber());
        // The event is not possible to test. It might only be with an integration test.

        verify(repository).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(repository).save(any(Client.class));
    }

    // To test the method update when we use an inexisting id
    @Test
    void updateInexistingIdTest() {

        Long idToUpdate = 202L;
        Client clientToUpdate = new Client(idToUpdate, "Angel", "Sanchez", "daltonico@idoidraw.com", 5526384734L, null, null);
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Optional<Client> result2 = service.update(idToUpdate, clientToUpdate);

        // Then
        assertFalse(result2.isPresent());
        assertThrows(NoSuchElementException.class, () -> {
            result2.orElseThrow();
        });

        verify(repository).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
        verify(repository, never()).save(any(Client.class));
    }

    // To test the method delete when we use an existing id
    @Test
    void deleteExistingIdTest() {

        // Given
        Long idToDelete = 1L;
        when(repository.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient001()));

        // When
        Optional<Client> result = service.deleteById(idToDelete);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Alejandro", result.get().getName());
        assertEquals("Granados", result.get().getLastname());
        assertEquals("alejandro.magb@gmail.com", result.get().getEmail());
        assertEquals(1538977020L, result.get().getPhonenumber());

        verify(repository).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(repository).deleteById(argThat(new CustomCondition(ClientData.idsValid, true)));
    }

    // To test the method delete when we use an inexisting id
    @Test
    void deleteInexistingIdTest() {

        // Given
        Long idToDelete = 9L;
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Optional<Client> result = service.deleteById(idToDelete);

        // Then
        assertFalse(result.isPresent());
        assertThrows(NoSuchElementException.class, () -> {
            result.orElseThrow();
        });

        verify(repository).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
        verify(repository, never()).deleteById(argThat(new CustomCondition(ClientData.idsValid, false)));
    }

}
