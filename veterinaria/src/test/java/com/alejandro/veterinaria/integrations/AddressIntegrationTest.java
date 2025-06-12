package com.alejandro.veterinaria.integrations;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import com.alejandro.veterinaria.entities.Address;
import com.alejandro.veterinaria.entities.Client;


// To load/insert the data on the file 'insert.sql'  
// To use the configurations on application-test.properties
// To start the test context with a random port
@Sql(scripts = "/insert.sql") 
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AddressIntegrationTest {
    
    // To inject the component of testRestTemplate
    @Autowired
    private TestRestTemplate client;

    // To test the endpoint getAddressByClient with an existing idClient and the client has an address
    @Test
    void getAddressByClientExistingIdIntegrationTest() {

        // Given 
        Long idClientToSearch = 21L;

        // When
        ResponseEntity<Address> response  = client.getForEntity("/api/clients/" + idClientToSearch + "/addresses", Address.class);
        Address address = response.getBody(); 

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(address);
        assertEquals(2001, address.getId());
        assertEquals("av. siempre viva", address.getStreet());
        assertEquals("venustiano carranza", address.getState());
        assertEquals("cdmx", address.getCity());
        assertEquals(56512L, address.getCp());

    }

    // To test the endpoint getAddressByClient with an existing idClient but the client does not have an address
    @Test
    void getAddressByClientExistingIdNoAddressIntegrationTest() {

        // Given 
        Long idClientToSearch = 51L;

        // When
        ResponseEntity<?> response  = client.getForEntity("/api/clients/" + idClientToSearch + "/addresses", Void.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint getAddressByClient with an inexisting id
    @Test
    void getAddressByClientInexistingIdIntegrationTest() {
    
        // Given
        Long idClientToSearch = 999999L;

        // When
        ResponseEntity<?> response  = client.getForEntity("/api/clients/" + idClientToSearch + "/addresses", Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint saveNewAddressByClientId when the idClient exists
    @Test
    void postSaveNewAddressByClientIdExistingIdIntegrationTest() {

        // Given
        Long idClientToSearch = 51L;
        Address addressToInsert = new Address(null, "independencia", "huchapan", "hidalgo", 45555L);

        // When
        ResponseEntity<Client> response = client.postForEntity("/api/clients/" + idClientToSearch + "/addresses", addressToInsert, Client.class);
        Client newClient = response.getBody();

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("John", newClient.getName());
        assertEquals("Lennon", newClient.getLastname());
        assertEquals("lennon@idoidraw.com", newClient.getEmail());
        assertEquals(4520895423L, newClient.getPhonenumber());

    }

    // To test the endpoint saveNewAddressByClientId when the idClient doesnt exist
    @Test
    void postSaveNewAddressByClientIdInexistingIdIntegrationTest() {
    
        // Given
        Long idClientToSearch = 999999L;
        Address addressToInsert = new Address(null, "independencia", "huchapan", "hidalgo", 45555L);

        // When
        ResponseEntity<?> response = client.postForEntity("/api/clients/" + idClientToSearch + "/addresses", addressToInsert, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint editAddressByClientId when the idClient exists
    @Test
    void putEditAddressByClientIdExistingIdIntegrationTest()  {

        // Given
        Long idClientToSearch = 61L;
        Address addressToUpdate = new Address(null, "san pancho", "chalco", "estado de mexico", 301245L);
        
        // When
        HttpEntity<Address> request = new HttpEntity<>(addressToUpdate);
        ResponseEntity<Client> response = client.exchange("/api/clients/" + idClientToSearch + "/addresses", HttpMethod.PUT, request, Client.class);

        // Then
        Client clientUpdate = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(61L, clientUpdate.getId());
        assertEquals("Alejandro", clientUpdate.getName());
        assertEquals("Granados", clientUpdate.getLastname());
        assertEquals("alejandro.magb@gmail.com", clientUpdate.getEmail());
        assertEquals(1538977020L, clientUpdate.getPhonenumber());

    }

    // To test the endpoint editAddressByClientId when the idClient doesnt exist
    @Test
    void putEditAddressByClientIdInexistingIdIntegrationTest()  {

        // Given
        Long idClientToSearch = 9999999L;
        Address addressToUpdate = new Address(null, "san pancho", "chalco", "estado de mexico", 301245L);
        
        // When
        HttpEntity<Address> request = new HttpEntity<>(addressToUpdate);
        ResponseEntity<?> response = client.exchange("/api/clients/" + idClientToSearch + "/addresses", HttpMethod.PUT, request, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

    // To test the endpoint deleteAddressByClientId when we use an existing id 
    @Test
    void deleteAddressByClientIdExistingIdIntegrationTest() {

        // Given
        Long idToDelete = 41L;
        
        // When
        ResponseEntity<Client> response = client.exchange("/api/clients/" + idToDelete + "/addresses", HttpMethod.DELETE, null, Client.class);

        // Then
        Client clientDelete = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(41L, clientDelete.getId());
        assertEquals("Esteban", clientDelete.getName());
        assertEquals("Gonzalez", clientDelete.getLastname());
        assertEquals("pastor34@idoidraw.com", clientDelete.getEmail());
        assertEquals(1234567890L, clientDelete.getPhonenumber());

        assertNull(clientDelete.getAddress());

    }

    // To test the endpoint deleteAddressByClientId when we use an inexisting id 
    @Test
    void deleteAddressByClientIdInexistingIdIntegrationTest() {

        // Given
        Long idToDelete = 999999L;
        
        // When
        ResponseEntity<?> response = client.exchange("/api/clients/" + idToDelete + "/addresses", HttpMethod.DELETE, null, Void.class);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

    }

}
