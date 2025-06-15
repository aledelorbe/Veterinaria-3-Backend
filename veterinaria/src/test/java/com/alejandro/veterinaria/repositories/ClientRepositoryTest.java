package com.alejandro.veterinaria.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.alejandro.veterinaria.entities.Client;

// To load the beans related to the persist layer.
// To load/insert the data on the file 'insert.sql'  
// To use the configurations on application-test.properties
@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "/insert.sql") 
class ClientRepositoryTest {
    
    @Autowired
    ClientRepository repository;

    @Test
    void findClientsByPetNameLikeTest () {

        // Given
        String nameToSearch = "goliath";

        // When
        List<Client> clients = repository.findClientsByPetNameLike(nameToSearch);

        // Then
        assertFalse(clients.isEmpty());
        assertEquals(2, clients.size());

        assertEquals(21L, clients.get(0).getId());
        assertEquals("Hueto", clients.get(0).getName());
        assertEquals("Navejas", clients.get(0).getLastname());
        assertEquals("hekevim148@idoidraw.com", clients.get(0).getEmail());
        assertEquals(1538971230L, clients.get(0).getPhonenumber());

        assertEquals(41L, clients.get(1).getId());
        assertEquals("Esteban", clients.get(1).getName());
        assertEquals("Gonzalez", clients.get(1).getLastname());
        assertEquals("pastor34@idoidraw.com", clients.get(1).getEmail());
        assertEquals(1234567890L, clients.get(1).getPhonenumber());
    }

}
