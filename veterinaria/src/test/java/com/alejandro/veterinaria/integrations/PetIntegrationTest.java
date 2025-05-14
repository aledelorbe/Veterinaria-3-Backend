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

import com.alejandro.veterinaria.entities.Pet;
import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.entities.ErrorMessage;


// To load/insert the data on the file 'insert.sql'  
// To use the configurations on application-test.properties
// To start the test context with a random port
@Sql(scripts = "/insert.sql") 
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PetIntegrationTest {
    
    // To inject the component of testRestTemplate
    @Autowired
    private TestRestTemplate client;

    // To test the endpoint getPetsByClient with an existing idClient
    @Test
    void getPetsByClientExistingIdIntegrationTest() {

        // Given 
        Long idClientToSearch = 21L;

        // When
        ResponseEntity<Pet[]> response  = client.getForEntity("/api/clients/" + idClientToSearch + "/pets", Pet[].class);
        List<Pet> pets = Arrays.asList(response.getBody());  

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(pets.isEmpty());
        assertEquals(3, pets.size());
        assertEquals(201, pets.get(0).getId());
        assertEquals("goliath", pets.get(0).getName());
        assertEquals("perro", pets.get(0).getSpecie());
        assertEquals("chihuahua", pets.get(0).getBreed());
        assertEquals(4L, pets.get(0).getAge());
        assertEquals("vomita mucho", pets.get(0).getReasonForVisit());

    }

    // To test the endpoint getPetsByClient with an inexisting id
    @Test
    void getPetsByClientInexistingIdIntegrationTest() {
    
        // Given
        Long idClientToSearch = 999999L;

        // When
        ResponseEntity<?> response  = client.getForEntity("/api/clients/" + idClientToSearch + "/pets", Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint saveNewPetByClientId when the idClient exists
    @Test
    void postSaveNewPetByClientIdExistingIdIntegrationTest() {

        // Given
        Long idClientToSearch = 51L;
        Pet petToInsert = new Pet(null, "rayas 2", "gato 2", "rayado 2", 15L, "tiene mucho sueño x2");

        // When
        ResponseEntity<Client> response = client.postForEntity("/api/clients/" + idClientToSearch + "/pets", petToInsert, Client.class);
        Client newClient = response.getBody();

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John", newClient.getName());
        assertEquals("Lennon", newClient.getLastname());
        assertEquals("lennon@idoidraw.com", newClient.getEmail());
        assertEquals(4520895423L, newClient.getPhonenumber());

    }

    // To test the endpoint saveNewPetByClientId when the idClient doesnt exist
    @Test
    void postSaveNewPetByClientIdInexistingIdIntegrationTest() {
    
        // Given
        Long idClientToSearch = 999999L;
        Pet petToInsert = new Pet(null, "rayas 2", "gato 2", "rayado 2", 15L, "tiene mucho sueño x2");

        // When
        ResponseEntity<?> response = client.postForEntity("/api/clients/" + idClientToSearch + "/pets", petToInsert, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint editPetByClientId when the idClient doesnt exist and the idPet exists
    @Test
    void putEditPetByClientIdInexistingIdClientIntegrationTest()  {

        // Given
        Long idClientToSearch = 999999L;
        Long idPetToSearch = 301L;
        Pet petToUpdate = new Pet(null, "rayas 3", "gato 3", "rayado 3", 15L, "tiene mucho sueño x3");
           
        // When
        HttpEntity<Pet> request = new HttpEntity<>(petToUpdate);
        ResponseEntity<?> response = client.exchange("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch, HttpMethod.PUT, request, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint editPetByClientId when the idClient exists and the idPet doesnt exist
    @Test
    void putEditPetByClientIdInexistingIdPetIntegrationTest() {
    
        // Given
        Long idClientToSearch = 31L;
        Long idPetToSearch = 999999L;
        Pet petToUpdate = new Pet(null, "rayas 3", "gato 3", "rayado 3", 15L, "tiene mucho sueño x3");

        // When
        HttpEntity<Pet> request = new HttpEntity<>(petToUpdate);
        ResponseEntity<?> response = client.exchange("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch, HttpMethod.PUT, request, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint 'editPetByClientId' when the idClient and idPet exist, but the pet was not updated
    @Test
    void putEditPetByClientIdExistingIdNoUpdatedIntegrationTest() {

        // Given
        Long idClientToSearch = 31L;
        Long idPetToSearch = 301L;
        Pet petToUpdate = new Pet(null, "rayas 3", "gato 3", "rayado 3", 15L, "tiene mucho sueño x3");
   
        // When
        HttpEntity<Pet> request = new HttpEntity<>(petToUpdate);
        ResponseEntity<?> response = client.exchange("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch, HttpMethod.PUT, request, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
   
    }

    // To test the endpoint 'editPetByClientId' when the idClient and idPet exist and the pet was updated
    @Test
    void putEditPetByClientIdExistingIdIntegrationTest() {

        // Given
        Long idClientToSearch = 31L;
        Long idPetToSearch = 501L;
        Pet petToUpdate = new Pet(null, "rayas 3", "gato 3", "rayado 3", 15L, "tiene mucho sueño x3");

        // When
        HttpEntity<Pet> request = new HttpEntity<>(petToUpdate);
        ResponseEntity<Client> response = client.exchange("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch, HttpMethod.PUT, request, Client.class);
        Client newClient = response.getBody();

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(31L, newClient.getId());
        assertEquals("Celia", newClient.getName());
        assertEquals("Bello", newClient.getLastname());
        assertEquals("cazador19@idoidraw.com", newClient.getEmail());
        assertEquals(1234977026L, newClient.getPhonenumber());

    }

    // To test the endpoint deletePetByClient when the idClient doesnt exist and the idPet exists
    @Test
    void deletePetByClientInexistingIdClientIntegrationTest()  {

        // Given
        Long idClientToSearch = 999999L;
        Long idPetToSearch = 301L;
    
        // When
        ResponseEntity<?> response = client.exchange("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch, HttpMethod.DELETE, null, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint deletePetByClient when the idClient exists and the idPet doesnt exist
    @Test
    void deletePetByClientInexistingIdPetIntegrationTest() {

        // Given
        Long idClientToSearch = 31L;
        Long idPetToSearch = 999999L;
      
        // When
        ResponseEntity<?> response = client.exchange("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch, HttpMethod.DELETE, null, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint 'deletePetByClient' when the idClient and idPet exist, but the pet was not updated
    @Test
    void deletePetByClientExistingIdNoUpdatedIntegrationTest() {

        // Given
        Long idClientToSearch = 31L;
        Long idPetToSearch = 301L;

        // When
        ResponseEntity<?> response = client.exchange("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch, HttpMethod.DELETE, null, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint 'deletePetByClient' when the idClient and idPet exist and the pet was updated
    @Test
    void deletePetByClientIdExistingIdIntegrationTest() {

        // Given
        Long idClientToSearch = 31L;
        Long idPetToSearch = 601L;
        
        // When
        ResponseEntity<Client> response = client.exchange("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch, HttpMethod.DELETE, null, Client.class);
        Client newClient = response.getBody();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(31L, newClient.getId());
        assertEquals("Celia", newClient.getName());
        assertEquals("Bello", newClient.getLastname());
        assertEquals("cazador19@idoidraw.com", newClient.getEmail());
        assertEquals(1234977026L, newClient.getPhonenumber());

    }

    // To test the validation in the DB: it is not allowed to insert a record with the same information as another pet of the same client
    @Test
    void petPostDuplicateRecordsIntegrationTest() {
    
        // Given
        Long idClientToSearch = 31L;
        Pet petToInsert = new Pet(null, "pennywise", "gato", "manchado", 5L, "tiene mucho sueño x3");

        // When
        ResponseEntity<ErrorMessage> response = client.postForEntity("/api/clients/" + idClientToSearch + "/pets", petToInsert, ErrorMessage.class);
        ErrorMessage newError = response.getBody();

        // Then
        assertEquals(HttpStatus.CONFLICT.value(), newError.getStatus());
        assertEquals("Error! Esta mascota ya se registro previamente para este cliente.", newError.getError());
        assertTrue(newError.getMessage().contains("PUBLIC.PET"));
        assertTrue(newError.getMessage().contains("[update pet set id_client=? where id_pet=?];"));

        LocalDateTime ahora = LocalDateTime.now();
        assertTrue( Duration.between(newError.getDateTime(), ahora).toMinutes() < 2 );

    }

    // To test the validation in the DB: it is not allowed to update a record with the same information as another pet of the same client
    @Test
    void petPutDuplicateRecordsIntegrationTest() {
    
        // Given
        Long idClientToSearch = 31L;
        Long idPetToSearch = 501L;
        Pet petToUpdate = new Pet(null, "pennywise", "gato", "manchado", 5L, "tiene mucho sueño x3");

        // When
        HttpEntity<Pet> request = new HttpEntity<>(petToUpdate);
        ResponseEntity<ErrorMessage> response = client.exchange("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch, HttpMethod.PUT, request, ErrorMessage.class);
        ErrorMessage newError = response.getBody();

        // Then
        assertEquals(HttpStatus.CONFLICT.value(), newError.getStatus());
        assertEquals("Error! Este nombre de mascota al cual se desea actualizar ya lo posee otra mascota de este mismo cliente.", newError.getError());
        assertTrue(newError.getMessage().contains("PUBLIC.PET"));
        assertFalse(newError.getMessage().contains("[update pet set id_client=? where id_pet=?];"));

        LocalDateTime ahora = LocalDateTime.now();
        assertTrue( Duration.between(newError.getDateTime(), ahora).toMinutes() < 2 );

    }

}
