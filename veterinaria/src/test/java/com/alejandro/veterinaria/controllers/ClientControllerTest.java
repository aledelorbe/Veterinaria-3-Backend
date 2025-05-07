package com.alejandro.veterinaria.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.alejandro.veterinaria.TestConfig;
import com.alejandro.veterinaria.data.ClientData;
import com.alejandro.veterinaria.data.CustomCondition;
import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ClientController.class)
@Import(TestConfig.class)
class ClientControllerTest {
    
    // To inject the dependency that allows for mocking HTTP requests
    @Autowired
    private MockMvc mockMvc;
 
    // To inject the dependency that represents the service to mock
    @Autowired
    private ClientService service; 

    @Autowired
    private ObjectMapper objectMapper;

    // To test the endpoint getClients
    @Test
    void getClientsTest () throws Exception {

        // Given
        when(service.findAll()).thenReturn(ClientData.createClients001());

        // When
        MvcResult result = mockMvc.perform(get("/api/clients"))

        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(5)))
        .andExpect(jsonPath("$[0].id").value(1L))
        .andExpect(jsonPath("$[0].name").value("Alejandro"))
        .andExpect(jsonPath("$[0].lastname").value("Granados"))
        .andExpect(jsonPath("$[0].email").value("alejandro.magb@gmail.com"))
        .andExpect(jsonPath("$[0].phonenumber").value(1538977020L))
        .andReturn()
        ;

        // Convert the response to a list of objects
        String jsonString = result.getResponse().getContentAsString();
        List<Client> clients = Arrays.asList(objectMapper.readValue(jsonString, Client[].class));

        assertFalse(clients.isEmpty());
        assertEquals(5, clients.size());
        assertEquals(1L, clients.get(0).getId());
        assertEquals("Alejandro", clients.get(0).getName());
        assertEquals("Granados", clients.get(0).getLastname());
        assertEquals("alejandro.magb@gmail.com", clients.get(0).getEmail());
        assertEquals(1538977020L, clients.get(0).getPhonenumber());

        verify(service).findAll();
    } 

    // To test the enpoint GetfindById with an existing id
    @Test
    void getfindByIdExistingIdTest() throws Exception {

        // Given
        Long idToSearch = 2L;
        when(service.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient002()));

        // When
        MvcResult result = mockMvc.perform(get("/api/clients/" + idToSearch))

        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andExpect(jsonPath("$.id").value(2L))
        .andExpect(jsonPath("$.name").value("Hueto"))
        .andExpect(jsonPath("$.lastname").value("Navejas"))
        .andExpect(jsonPath("$.email").value("hekevim148@idoidraw.com"))
        .andExpect(jsonPath("$.phonenumber").value(1538971230L))
        .andReturn()
        ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Client client = objectMapper.readValue(jsonString, Client.class);

        assertNotNull(client);
        assertEquals(2L, client.getId());
        assertEquals("Hueto", client.getName());
        assertEquals("Navejas", client.getLastname());
        assertEquals("hekevim148@idoidraw.com", client.getEmail());
        assertEquals(1538971230L, client.getPhonenumber());

        verify(service).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
    }
    
    // To test the enpoint GetfindById with an inexisting id
    @Test
    void getfindByIdInexistingIdTest() throws Exception {
        
        // Given
        Long idToSearch = 999999L;
        when(service.findById(anyLong())).thenReturn(Optional.empty());
        
        // When
        mockMvc.perform(get("/api/clients/" + idToSearch))
        
        // Then
        .andExpect(status().isNotFound())
        .andExpect(content().string(""))
        ;

        verify(service).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
    }

    // To test the endpoint save
    @Test
    void postSaveTest() throws Exception {

        // Given
        Client clientInsert = new Client(null, "ben", "tennison", "be123@gmail.com", 1238977020L, null, null);
        when(service.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // when
        MvcResult result = mockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(clientInsert)))

        // then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("ben"))
            .andExpect(jsonPath("$.lastname").value("tennison"))
            .andExpect(jsonPath("$.email").value("be123@gmail.com"))
            .andExpect(jsonPath("$.phonenumber").value(1238977020L))
            .andReturn()
            ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Client newClient = objectMapper.readValue(jsonString, Client.class);

        assertEquals("ben", newClient.getName());
        assertEquals("tennison", newClient.getLastname());
        assertEquals("be123@gmail.com", newClient.getEmail());
        assertEquals(1238977020L, newClient.getPhonenumber());

        verify(service).save(any(Client.class));
    }

    // To test the endpoint update when we use an existing id 
    @Test
    void putUpdateExistingIdTest() throws Exception {
    
        // Given
        Long idToUpdate = 2L;
        Client clientToUpdate = new Client(null, "wen", "tennison", "wen456@gmail.com", 4568977020L, null, null);
        when(service.update(anyLong(), any(Client.class))).thenAnswer(invocation -> Optional.of(invocation.getArgument(1)));

        // When
        MvcResult result = mockMvc.perform(put("/api/clients/" + idToUpdate)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(clientToUpdate)))

        // then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("wen"))
            .andExpect(jsonPath("$.lastname").value("tennison"))
            .andExpect(jsonPath("$.email").value("wen456@gmail.com"))
            .andExpect(jsonPath("$.phonenumber").value(4568977020L))
            .andReturn()
            ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Client newClient = objectMapper.readValue(jsonString, Client.class);

        assertEquals("wen", newClient.getName());
        assertEquals("tennison", newClient.getLastname());
        assertEquals("wen456@gmail.com", newClient.getEmail());
        assertEquals(4568977020L, newClient.getPhonenumber());

        verify(service).update(argThat(new CustomCondition(ClientData.idsValid, true)), any(Client.class));
    }

    // To test the endpoint update when we use an inexisting id 
    @Test
    void putUpdateInexistingIdTest() throws Exception {
    
        // Given
        Long idToUpdate = 8L;
        Client clientToUpdate = new Client(null, "wen", "tennison", "wen456@gmail.com", 4568977020L, null, null);
        when(service.update(anyLong(), any(Client.class))).thenReturn(Optional.empty());

        // When
        mockMvc.perform(put("/api/clients/" + idToUpdate)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(clientToUpdate)))

        // then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
            ;

        verify(service).update(argThat(new CustomCondition(ClientData.idsValid, false)), any(Client.class));
    }

    // To test the endpoint delete when we use an existing id 
    @Test
    void deleteExistingIdTest() throws Exception {
    
        // Given
        Long idToDelete = 1L;
        when(service.deleteById(anyLong())).thenReturn(Optional.of(ClientData.createClient001()));

        // When
        MvcResult result = mockMvc.perform(delete("/api/clients/" + idToDelete))

        // then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Alejandro"))
            .andExpect(jsonPath("$.lastname").value("Granados"))
            .andExpect(jsonPath("$.email").value("alejandro.magb@gmail.com"))
            .andExpect(jsonPath("$.phonenumber").value(1538977020L))
            .andReturn()
            ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Client newClient = objectMapper.readValue(jsonString, Client.class);

        assertEquals("Alejandro", newClient.getName());
        assertEquals("Granados", newClient.getLastname());
        assertEquals("alejandro.magb@gmail.com", newClient.getEmail());
        assertEquals(1538977020L, newClient.getPhonenumber());

        verify(service).deleteById(argThat(new CustomCondition(ClientData.idsValid, true)));
    }

    // To test the endpoint delete when we use an inexisting id 
    @Test
    void deleteInexistingIdTest() throws Exception {
    
        // Given
        Long idToDelete = 99999L;
        when(service.deleteById(anyLong())).thenReturn(Optional.empty());

        // When
        mockMvc.perform(delete("/api/clients/" + idToDelete))

        // then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
            ;

        verify(service).deleteById(argThat(new CustomCondition(ClientData.idsValid, false)));
    }

    // To test the method validation
    @Test
    void validationTest() throws Exception {

        // Given
        Client clientInsert = new Client(null, "", "", "wen456_gmai", 12345L, null, null);
        
        // when
        mockMvc.perform(post("/api/clients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(clientInsert)))
        
        // then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.name").value("El campo name must not be blank"))
            .andExpect(jsonPath("$.lastname").value("El campo lastname must not be blank"))
            .andExpect(jsonPath("$.email").value("El campo email must be a well-formed email address"))
            .andExpect(jsonPath("$.phonenumber").value("El campo phonenumber deben ser 10 digitos"))
            ;

        verify(service, never()).save(any(Client.class));
    }

}
