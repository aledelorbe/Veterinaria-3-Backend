package com.alejandro.veterinaria.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.alejandro.veterinaria.TestConfig;
import com.alejandro.veterinaria.data.PetData;
import com.alejandro.veterinaria.data.ClientData;
import com.alejandro.veterinaria.data.CustomCondition;
import com.alejandro.veterinaria.entities.Pet;
import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.services.PetService;
import com.alejandro.veterinaria.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PetController.class)
@Import(TestConfig.class)
class PetControllerTest {
    
    // To inject the dependency that allows for mocking HTTP requests
    @Autowired
    private MockMvc mockMvc;
 
    // To inject the dependency that represents the service to mock
    @MockitoBean
    private PetService service; 

    // To inject the dependency that represents the service to mock
    @MockitoBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    // To test the endpoint getPetsByClient with an existing idClient
    @Test
    void getPetsByClientExistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 2L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient002()));
        when(service.getPetsByClient(any(Client.class))).thenReturn(PetData.createPets002());

        // When
        MvcResult result = mockMvc.perform(get("/api/clients/" + idClientToSearch + "/pets"))

        // Then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].id").value(20L))
            .andExpect(jsonPath("$[0].name").value("goliath"))
            .andExpect(jsonPath("$[0].specie").value("perro"))
            .andExpect(jsonPath("$[0].breed").value("chihuahua"))
            .andExpect(jsonPath("$[0].age").value(4L))
            .andExpect(jsonPath("$[0].reasonForVisit").value("vomita mucho"))
            .andReturn()
        ;

        // Convert the response to an array of objects
        String jsonString = result.getResponse().getContentAsString();
        List<Pet> pets = Arrays.asList( objectMapper.readValue(jsonString, Pet[].class));

        assertNotNull(pets);
        assertEquals(20L, pets.get(0).getId());
        assertEquals("goliath", pets.get(0).getName());
        assertEquals("perro", pets.get(0).getSpecie());
        assertEquals("chihuahua", pets.get(0).getBreed());
        assertEquals(4L, pets.get(0).getAge());
        assertEquals("vomita mucho", pets.get(0).getReasonForVisit());

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(service).getPetsByClient(any(Client.class));
    }

    // To test the endpoint getPetByClient with an inexisting id
    @Test
    void getPetByClientInexistingIdTest() throws Exception {
        
        // Given
        Long idClientToSearch = 999999L;
        when(clientService.findById(anyLong())).thenReturn(Optional.empty());
        
        // When
        mockMvc.perform(get("/api/clients/" + idClientToSearch + "/pets"))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
            ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
        verify(service, never()).getPetsByClient(any(Client.class));
    }

    // To test the endpoint saveNewPetByClientId when the idClient exists
    @Test
    void postSaveNewPetByClientIdExistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 5L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient005()));
        when(service.savePetByClient(any(Client.class), any(Pet.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Pet petToInsert = new Pet(null, "rayas 2", "gato 2", "rayado 2", 15L, "tiene mucho sueño x2");
        
        // When
        MvcResult result = mockMvc.perform(post("/api/clients/" + idClientToSearch + "/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(petToInsert)))

        // Then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.id").value(5L))
            .andExpect(jsonPath("$.name").value("John"))
            .andExpect(jsonPath("$.lastname").value("Lennon"))
            .andExpect(jsonPath("$.email").value("lennon@idoidraw.com"))
            .andExpect(jsonPath("$.phonenumber").value(45208954L))
            .andReturn()
        ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Client client = objectMapper.readValue(jsonString, Client.class);

        assertNotNull(client);
        assertEquals(5L, client.getId());
        assertEquals("John", client.getName());
        assertEquals("Lennon", client.getLastname());
        assertEquals("lennon@idoidraw.com", client.getEmail());
        assertEquals(45208954L, client.getPhonenumber());

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(service).savePetByClient(any(Client.class), any(Pet.class));
    }

    // To test the endpoint saveNewPetByClientId when the idClient doesnt exist
    @Test
    void postSaveNewPetByClientIdInexistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 999999L;
        when(clientService.findById(anyLong())).thenReturn(Optional.empty());
        Pet petToInsert = new Pet(null, "rayas 2", "gato 2", "rayado 2", 15L, "tiene mucho sueño x2");
        
        // When
        mockMvc.perform(post("/api/clients/" + idClientToSearch + "/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(petToInsert)))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
        verify(service, never()).savePetByClient(any(Client.class), any(Pet.class));
    }

    // To test the endpoint editPetByClientId when the idClient doesnt exist and the idPet exists
    @Test
    void putEditPetByClientIdInexistingIdClientTest() throws Exception {

        // Given
        Long idClientToSearch = 999999L;
        Long idPetToSearch = 30L;
        when(clientService.findById(anyLong())).thenReturn(Optional.empty());
        when(service.findById(anyLong())).thenReturn(Optional.of(PetData.createPet003()));
        Pet petToUpdate = new Pet(null, "rayas 3", "gato 3", "rayado 3", 15L, "tiene mucho sueño x3");
        
        // When
        mockMvc.perform(put("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(petToUpdate)))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
        verify(service).findById(argThat(new CustomCondition(PetData.idsValid, true)));
        verify(service, never()).editPetByClient(any(Client.class), anyLong(), any(Pet.class));
    }

    // To test the endpoint editPetByClientId when the idClient exists and the idPet doesnt exist
    @Test
    void putEditPetByClientIdInexistingIdPetTest() throws Exception {

        // Given
        Long idClientToSearch = 3L;
        Long idPetToSearch = 999999L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient003()));
        when(service.findById(anyLong())).thenReturn(Optional.empty());
        Pet petToUpdate = new Pet(null, "rayas 3", "gato 3", "rayado 3", 15L, "tiene mucho sueño x3");
        
        // When
        mockMvc.perform(put("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(petToUpdate)))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(service).findById(argThat(new CustomCondition(PetData.idsValid, false)));
        verify(service, never()).editPetByClient(any(Client.class), anyLong(), any(Pet.class));
    }

    // To test the endpoint 'editPetByClientId' when the idClient and idPet exist, but the pet was not updated
    @Test
    void putEditPetByClientIdExistingIdNoUpdatedTest() throws Exception {

        // Given
        Long idClientToSearch = 3L;
        Long idPetToSearch = 30L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient003()));
        when(service.findById(anyLong())).thenReturn(Optional.of(PetData.createPet003()));
        Pet petToUpdate = new Pet(null, "rayas 3", "gato 3", "rayado 3", 15L, "tiene mucho sueño x3");
        when(service.editPetByClient(any(Client.class), anyLong(), any(Pet.class))).thenReturn(Optional.empty());

        // When
        mockMvc.perform(put("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(petToUpdate)))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(service).findById(argThat(new CustomCondition(PetData.idsValid, true)));
        verify(service).editPetByClient(any(Client.class), anyLong(), any(Pet.class));
    }

    // To test the endpoint 'editPetByClientId' when the idClient and idPet exist and the pet was updated
    @Test
    void putEditPetByClientIdExistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 3L;
        Long idPetToSearch = 30L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient003()));
        when(service.findById(anyLong())).thenReturn(Optional.of(PetData.createPet003()));
        Pet petToUpdate = new Pet(null, "rayas 3", "gato 3", "rayado 3", 15L, "tiene mucho sueño x3");
        when(service.editPetByClient(any(Client.class), anyLong(), any(Pet.class))).thenAnswer(invocation -> Optional.of(invocation.getArgument(0)) );

        // When
        MvcResult result = mockMvc.perform(put("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(petToUpdate)))
        
        // Then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.id").value(3L))
            .andExpect(jsonPath("$.name").value("Celia"))
            .andExpect(jsonPath("$.lastname").value("Bello"))
            .andExpect(jsonPath("$.email").value("cazador19@idoidraw.com"))
            .andExpect(jsonPath("$.phonenumber").value(1234977026L))
            .andReturn()
        ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Client client = objectMapper.readValue(jsonString, Client.class);

        assertNotNull(client);
        assertEquals(3L, client.getId());
        assertEquals("Celia", client.getName());
        assertEquals("Bello", client.getLastname());
        assertEquals("cazador19@idoidraw.com", client.getEmail());
        assertEquals(1234977026L, client.getPhonenumber());

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(service).findById(argThat(new CustomCondition(PetData.idsValid, true)));
        verify(service).editPetByClient(any(Client.class), anyLong(), any(Pet.class));
    }

    // To test the endpoint deletePetByClient when the idClient doesnt exist and the idPet exists
    @Test
    void deletePetByClientInexistingIdClientTest() throws Exception {

        // Given
        Long idClientToSearch = 999999L;
        Long idPetToSearch = 30L;
        when(clientService.findById(anyLong())).thenReturn(Optional.empty());
        when(service.findById(anyLong())).thenReturn(Optional.of(PetData.createPet003()));
        
        // When
        mockMvc.perform(delete("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
        verify(service).findById(argThat(new CustomCondition(PetData.idsValid, true)));
        verify(service, never()).deletePetByClient(any(Client.class), anyLong());
    }

    // To test the endpoint deletePetByClient when the idClient exists and the idPet doesnt exist
    @Test
    void deletePetByClientInexistingIdPetTest() throws Exception {

        // Given
        Long idClientToSearch = 3L;
        Long idPetToSearch = 999999L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient003()));
        when(service.findById(anyLong())).thenReturn(Optional.empty());
        
        // When
        mockMvc.perform(delete("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(service).findById(argThat(new CustomCondition(PetData.idsValid, false)));
        verify(service, never()).deletePetByClient(any(Client.class), anyLong());
    }

    // To test the endpoint 'deletePetByClient' when the idClient and idPet exist, but the pet was not updated
    @Test
    void deletePetByClientExistingIdNoUpdatedTest() throws Exception {

        // Given
        Long idClientToSearch = 3L;
        Long idPetToSearch = 30L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient003()));
        when(service.findById(anyLong())).thenReturn(Optional.of(PetData.createPet003()));
        when(service.deletePetByClient(any(Client.class), anyLong())).thenReturn(Optional.empty());

        // When
        mockMvc.perform(delete("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(service).findById(argThat(new CustomCondition(PetData.idsValid, true)));
        verify(service).deletePetByClient(any(Client.class), anyLong());
    }

    // To test the endpoint 'deletePetByClient' when the idClient and idPet exist and the pet was updated
    @Test
    void deletePetByClientIdExistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 3L;
        Long idPetToSearch = 30L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient003()));
        when(service.findById(anyLong())).thenReturn(Optional.of(PetData.createPet003()));
        when(service.deletePetByClient(any(Client.class), anyLong())).thenAnswer(invocation -> Optional.of(invocation.getArgument(0)) );

        // When
        MvcResult result = mockMvc.perform(delete("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch))
        
        // Then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.id").value(3L))
            .andExpect(jsonPath("$.name").value("Celia"))
            .andExpect(jsonPath("$.lastname").value("Bello"))
            .andExpect(jsonPath("$.email").value("cazador19@idoidraw.com"))
            .andExpect(jsonPath("$.phonenumber").value(1234977026L))
            .andReturn()
        ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Client client = objectMapper.readValue(jsonString, Client.class);

        assertNotNull(client);
        assertEquals(3L, client.getId());
        assertEquals("Celia", client.getName());
        assertEquals("Bello", client.getLastname());
        assertEquals("cazador19@idoidraw.com", client.getEmail());
        assertEquals(1234977026L, client.getPhonenumber());

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(service).findById(argThat(new CustomCondition(PetData.idsValid, true)));
        verify(service).deletePetByClient(any(Client.class), anyLong());
    }

    // To test the method validation
    @Test
    void validationTest() throws Exception {

        // Given
        Long idClientToSearch = 5L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient005()));
        when(service.savePetByClient(any(Client.class), any(Pet.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Pet petToInsert = new Pet(null, "", "", "", null, "");
        
        // When
        mockMvc.perform(post("/api/clients/" + idClientToSearch + "/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(petToInsert)))
        
        // then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.name").value("El campo name must not be blank"))
            .andExpect(jsonPath("$.specie").value("El campo specie must not be blank"))
            .andExpect(jsonPath("$.age").value("El campo age must not be null"))
            .andExpect(jsonPath("$.reasonForVisit").value("El campo reasonForVisit must not be blank"))
        ;

        verify(clientService, never()).findById(anyLong());
        verify(service, never()).savePetByClient(any(Client.class), any(Pet.class));
    }

}
