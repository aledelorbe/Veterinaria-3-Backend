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
import com.alejandro.veterinaria.data.ClientData;
import com.alejandro.veterinaria.data.CustomCondition;
import com.alejandro.veterinaria.data.PetData;
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

    // To test the 'getPetsByClient' endpoint with an existing idClient
    @Test
    void getPetsByClientExistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 2L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient002()));

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
    }

    // To test the 'getPetsByClient' endpoint with an inexisting idClient
    @Test
    void getPetsByClientInexistingIdTest() throws Exception {
        
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
    }

    // To test the 'saveNewPetByClientId' endpoint when the idClient exists
    @Test
    void postSaveNewPetByClientIdExistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 5L;
        when(service.savePetByClient(anyLong(), any(Pet.class))).thenReturn(Optional.of(ClientData.createClient005()));
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

        verify(service).savePetByClient(argThat(new CustomCondition(ClientData.idsValid, true)), any(Pet.class));
    }

    // To test the 'saveNewPetByClientId' endpoint when the idClient doesnt exist
    @Test
    void postSaveNewPetByClientIdInexistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 999999L;
        when(service.savePetByClient(anyLong(), any(Pet.class))).thenReturn(Optional.empty());
        Pet petToInsert = new Pet(null, "rayas 2", "gato 2", "rayado 2", 15L, "tiene mucho sueño x2");
        
        // When
        mockMvc.perform(post("/api/clients/" + idClientToSearch + "/pets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(petToInsert)))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(service).savePetByClient(argThat(new CustomCondition(ClientData.idsValid, false)), any(Pet.class));
    }

    // To test the 'editPetByClientId' endpoint when the pet can be updated
    @Test
    void putEditPetByClientIdSuccessUpdateTest() throws Exception {

        // Given
        Long idClientToSearch = 3L;
        Long idPetToSearch = 30L;
        when(service.editPetByClient(anyLong(), anyLong(), any(Pet.class))).thenReturn(Optional.of(ClientData.createClient003()));
        Pet petToUpdate = new Pet(null, "rayas 3", "gato 3", "rayado 3", 15L, "tiene mucho sueño x3");

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

        verify(service).editPetByClient(argThat(new CustomCondition(ClientData.idsValid, true)), argThat(new CustomCondition(PetData.idsValid, true)), any(Pet.class));
    }

    // To test the 'editPetByClientId' endpoint when the pet can not be updated
    @Test
    void putEditPetByClientIdUnsuccessUpdateTest() throws Exception {

        // Given
        Long idClientToSearch = 99999L;
        Long idPetToSearch = 999999L;
        when(service.editPetByClient(anyLong(), anyLong(), any(Pet.class))).thenReturn(Optional.empty());
        Pet petToUpdate = new Pet(null, "rayas 3", "gato 3", "rayado 3", 15L, "tiene mucho sueño x3");
        
        // When
        mockMvc.perform(put("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(petToUpdate)))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(service).editPetByClient(argThat(new CustomCondition(ClientData.idsValid, false)), argThat(new CustomCondition(PetData.idsValid, false)), any(Pet.class));
    }

    // To test the 'deletePetByClient' endpoint when the pet can be deleted
    @Test
    void deletePetByClientIdSuccessDeleteTest() throws Exception {

        // Given
        Long idClientToSearch = 3L;
        Long idPetToSearch = 30L;
        when(service.deletePetByClient(anyLong(), anyLong())).thenReturn(Optional.of(ClientData.createClient003()));

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

        verify(service).deletePetByClient(argThat(new CustomCondition(ClientData.idsValid, true)), argThat(new CustomCondition(PetData.idsValid, true)));
    }

    // To test the 'deletePetByClient' endpoint when the pet can not be deleted
    @Test
    void deletePetByClientIdUnsuccessDeleteTest() throws Exception {

        // Given
        Long idClientToSearch = 99999L;
        Long idPetToSearch = 99999L;
        when(service.deletePetByClient(anyLong(), anyLong())).thenReturn(Optional.empty());

        // When
        mockMvc.perform(delete("/api/clients/" + idClientToSearch + "/pets/" + idPetToSearch))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(service).deletePetByClient(argThat(new CustomCondition(ClientData.idsValid, false)), argThat(new CustomCondition(PetData.idsValid, false)));
    }

    // To test the 'validation' method
    @Test
    void validationTest() throws Exception {

        // Given
        Long idClientToSearch = 5L;
        when(service.savePetByClient(anyLong(), any(Pet.class))).thenReturn(Optional.of(ClientData.createClient005()));
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

        verify(service, never()).savePetByClient(argThat(new CustomCondition(ClientData.idsValid, true)), any(Pet.class));
    }

}
