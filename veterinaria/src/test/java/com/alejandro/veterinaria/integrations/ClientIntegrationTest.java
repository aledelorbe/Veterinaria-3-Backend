package com.alejandro.veterinaria.integrations;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.entities.ErrorMessage;

// To load/insert the data on the file 'insert.sql'  
// To use the configurations on application-test.properties
// To start the test context with a random port
@Sql(scripts = "/insert.sql") 
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ClientIntegrationTest {
    
    // To inject the component of testRestTemplate
    @Autowired
    private TestRestTemplate client;

    // To test the endpoint getClients
    @Test
    void getClientsIntegrationTest() {

        // When
        ResponseEntity<Client[]> response  = client.getForEntity("/api/clients", Client[].class);
        List<Client> clients = Arrays.asList(response.getBody()); 

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(clients.isEmpty());
        assertEquals(5, clients.size());
        assertEquals(21L, clients.get(0).getId());
        assertEquals("Hueto", clients.get(0).getName());
        assertEquals("Navejas", clients.get(0).getLastname());
        assertEquals("hekevim148@idoidraw.com", clients.get(0).getEmail());
        assertEquals(1538971230L, clients.get(0).getPhonenumber());

    }

    // To test the endpoint getClient when we use an existing id
    @Test
    void getClientExistingIdIntegrationTest() {
    
        // Given
        Long idToSearch = 61L;

        // When
        ResponseEntity<Client> response  = client.getForEntity("/api/clients/" + idToSearch, Client.class);
        Client clientSearched = response.getBody(); 

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(61L, clientSearched.getId());
        assertEquals("Alejandro", clientSearched.getName());
        assertEquals("Granados", clientSearched.getLastname());
        assertEquals("alejandro.magb@gmail.com", clientSearched.getEmail());
        assertEquals(1538977020, clientSearched.getPhonenumber());

    }

    // To test the endpoint getClient when we use an inexisting id
    @Test
    void getClientInexistingIdIntegrationTest() {
    
        // Given
        Long idToSearch = 99999L;

        // When
        ResponseEntity<?> response  = client.getForEntity("/api/clients/" + idToSearch, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        
    }

    // To test the endpoint save
    @Test
    void postClientIntegrationTest() {

        // Given
        Client clientInsert = new Client(null, " Javier ", " Mejia ", "enjambre@idoidraw.com", 5550374984L, null, null);

        // When
        ResponseEntity<Client> response = client.postForEntity("/api/clients", clientInsert, Client.class);
        Client newClient = response.getBody();

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Javier", newClient.getName());
        assertEquals("Mejia", newClient.getLastname());
        assertEquals("enjambre@idoidraw.com", newClient.getEmail());
        assertEquals(5550374984L, newClient.getPhonenumber());

    }

    // To test the endpoint update when we use an existing id 
    @Test
    void putUpdateExistingIdIntegrationTest()  {

        // Given
        Long idToUpdate = 51L;
        Client clientToUpdate = new Client(null, " Javier 2 ", " Mejia 2 ", "enjambre2@idoidraw.com", 5550374985L, null, null);
        
        // When
        HttpEntity<Client> request = new HttpEntity<>(clientToUpdate);
        ResponseEntity<Client> response = client.exchange("/api/clients/" + idToUpdate, HttpMethod.PUT, request, Client.class);

        // Then
        Client clientUpdate = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(51L, clientUpdate.getId());
        assertEquals("Javier 2", clientUpdate.getName());
        assertEquals("Mejia 2", clientUpdate.getLastname());
        assertEquals("enjambre2@idoidraw.com", clientUpdate.getEmail());
        assertEquals(5550374985L, clientUpdate.getPhonenumber());

    }

    // To test the endpoint update when we use an inexisting id 
    @Test
    void putUpdateInexistingIdIntegrationTest()  {

        // Given
        Long idToUpdate = 999999L;
        Client clientToUpdate = new Client(null, " Javier 2 ", " Mejia 2 ", "enjambre2@idoidraw.com", 5550374985L, null, null);
        HttpEntity<Client> request = new HttpEntity<>(clientToUpdate);
        
        // When
        ResponseEntity<?> response = client.exchange("/api/clients/" + idToUpdate, HttpMethod.PUT, request, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint delete when we use an existing id 
    @Test
    void deleteExistingIdIntegrationTest() {

        // Given
        Long idToDelete = 41L;
        
        // When
        ResponseEntity<Client> response = client.exchange("/api/clients/" + idToDelete, HttpMethod.DELETE, null, Client.class);

        // Then
        Client clientDelete = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(41L, clientDelete.getId());
        assertEquals("Esteban", clientDelete.getName());
        assertEquals("Gonzalez", clientDelete.getLastname());
        assertEquals("pastor34@idoidraw.com", clientDelete.getEmail());
        assertEquals(1234567890L, clientDelete.getPhonenumber());

        // When
        ResponseEntity<Client[]> response2  = client.getForEntity("/api/clients", Client[].class);
        List<Client> clients = Arrays.asList(response2.getBody()); 

        // Then
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertFalse(clients.isEmpty());
        assertEquals(4, clients.size());
        
    }

    // To test the endpoint delete when we use an inexisting id 
    @Test
    void deleteInexistingIdIntegrationTest() {
    
        // Given
        Long idToDelete = 999999L;
        
        // When
        ResponseEntity<?> response = client.exchange("/api/clients/" + idToDelete, HttpMethod.DELETE, null, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint getClientsByPetNameLike
    @Test
    void getClientsByPetNameLikeIntegrationTest() {
    
        // Given
        String nameToSearch = "goliath";

        // When
        ResponseEntity<Client[]> response  = client.getForEntity("/api/clients/pets/" + nameToSearch, Client[].class);
        List<Client> clients = Arrays.asList(response.getBody()); 

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
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

    // To test the validation in the DB: it doesn't allow to insert the same record
    @Test
    void clientPostDuplicateRecordsIntegrationTest() {
    
        // Given
        Client clientToDuplicate = new Client(null, " Alejandro ", " Granados ", "enjambre@idoidraw.com", 5550374984L, null, null);

        // When
        ResponseEntity<ErrorMessage> response2 = client.postForEntity("/api/clients", clientToDuplicate, ErrorMessage.class);
        ErrorMessage newError = response2.getBody();

        // Then
        assertEquals(HttpStatus.CONFLICT.value(), newError.getStatus());
        assertEquals("Error! El cliente que se desea registrar ya se encuentra en la base de datos.", newError.getError());
        assertTrue(newError.getMessage().contains("insert"));
        assertTrue(newError.getMessage().contains("PUBLIC.UK_CLIENT"));

        LocalDateTime ahora = LocalDateTime.now();
        assertTrue( Duration.between(newError.getDateTime(), ahora).toMinutes() < 2 );

    }

    // To test the validation in the DB: it doesn't allow to update a record with the same information as another one
    @Test
    void clientPutDuplicateRecordsIntegrationTest() {
    
        // Given
        Long idToUpdate = 51L;
        Client clientToUpdate = new Client(null, " Celia ", " Bello ", "enjambre2@idoidraw.com", 5550374985L, null, null);
        
        // When
        HttpEntity<Client> request = new HttpEntity<>(clientToUpdate);
        ResponseEntity<ErrorMessage> response = client.exchange("/api/clients/" + idToUpdate, HttpMethod.PUT, request, ErrorMessage.class);
        ErrorMessage newError = response.getBody();

        // Then
        assertEquals("Error! Este nombre de cliente al cual se desea actualizar ya lo posee otro cliente.", newError.getError());
        assertEquals(HttpStatus.CONFLICT.value(), newError.getStatus());
        assertTrue(newError.getMessage().contains("update"));
        assertTrue(newError.getMessage().contains("PUBLIC.UK_CLIENT"));

        LocalDateTime ahora = LocalDateTime.now();
        assertTrue( Duration.between(newError.getDateTime(), ahora).toMinutes() < 2 );

    }

}
