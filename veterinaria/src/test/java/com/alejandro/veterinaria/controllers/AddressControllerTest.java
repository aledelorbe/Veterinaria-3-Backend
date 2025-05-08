package com.alejandro.veterinaria.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import com.alejandro.veterinaria.data.AddressData;
import com.alejandro.veterinaria.data.ClientData;
import com.alejandro.veterinaria.data.CustomCondition;
import com.alejandro.veterinaria.entities.Address;
import com.alejandro.veterinaria.entities.Client;
import com.alejandro.veterinaria.services.AddressService;
import com.alejandro.veterinaria.services.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AddressController.class)
@Import(TestConfig.class)
class AddressControllerTest {

    // To inject the dependency that allows for mocking HTTP requests
    @Autowired
    private MockMvc mockMvc;
 
    // To inject the dependency that represents the service to mock
    @MockitoBean
    private AddressService service; 

    // To inject the dependency that represents the service to mock
    @MockitoBean
    private ClientService clientService;

    @Autowired
    private ObjectMapper objectMapper;

    // To test the endpoint getAddressByClient with an existing idClient and the client has an address
    @Test
    void getAddressByClientExistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 2L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient002()));
        when(service.getAddressByClient(any(Client.class))).thenReturn(Optional.of(AddressData.createAddress002()));

        // When
        MvcResult result = mockMvc.perform(get("/api/clients/" + idClientToSearch + "/address"))

        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isNotEmpty())
        .andExpect(jsonPath("$.id").value(200L))
        .andExpect(jsonPath("$.street").value("av. siempre viva"))
        .andExpect(jsonPath("$.state").value("venustiano carranza"))
        .andExpect(jsonPath("$.city").value("cdmx"))
        .andExpect(jsonPath("$.cp").value(56512L))
        .andReturn()
        ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Address address = objectMapper.readValue(jsonString, Address.class);

        assertNotNull(address);
        assertEquals(200L, address.getId());
        assertEquals("av. siempre viva", address.getStreet());
        assertEquals("venustiano carranza", address.getState());
        assertEquals("cdmx", address.getCity());
        assertEquals(56512L, address.getCp());

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(service).getAddressByClient(any(Client.class));
    }

    // To test the endpoint getAddressByClient with an existing idClient but the client does not have an address
    @Test
    void getAddressByClientExistingIdNoAddressTest() throws Exception {

        // Given
        Long idClientToSearch = 2L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient002()));
        when(service.getAddressByClient(any(Client.class))).thenReturn(Optional.empty());

        // When
        mockMvc.perform(get("/api/clients/" + idClientToSearch + "/address"))

        // Then
            .andExpect(status().isNoContent())
            .andExpect(content().string(""))
            ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(service).getAddressByClient(any(Client.class));
    }
    
    // To test the endpoint getAddressByClientTest with an inexisting id
    @Test
    void getAddressByClientInexistingIdTest() throws Exception {
        
        // Given
        Long idClientToSearch = 999999L;
        when(clientService.findById(anyLong())).thenReturn(Optional.empty());
        
        // When
        mockMvc.perform(get("/api/clients/" + idClientToSearch + "/address"))
        
        // Then
        .andExpect(status().isNotFound())
        .andExpect(content().string(""))
        ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
    }

    // To test the endpoint save when the idClient exists
    @Test
    void postSaveNewAddressByClientIdExistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 5L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient005()));
        when(service.saveAddressByClient(any(Client.class), any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Address addressToInsert = new Address(null, "independencia", "huchapan", "hidalgo", 45555L);
        
        // When
        MvcResult result = mockMvc.perform(post("/api/clients/" + idClientToSearch + "/address")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(addressToInsert)))

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
        verify(service).saveAddressByClient(any(Client.class), any(Address.class));
    }

    // To test the endpoint save when the idClient doesnt exist
    @Test
    void postSaveNewAddressByClientIdInexistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 999999L;
        when(clientService.findById(anyLong())).thenReturn(Optional.empty());
        Address addressToInsert = new Address(null, "independencia", "huchapan", "hidalgo", 45555L);
        
        // When
        mockMvc.perform(post("/api/clients/" + idClientToSearch + "/address")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(addressToInsert)))
        
        // Then
        .andExpect(status().isNotFound())
        .andExpect(content().string(""))
        ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
    }

    // To test the endpoint editAddressByClientId when the idClient exists
    @Test
    void putEditAddressByClientIdExistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 1L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient001()));
        when(service.editAddressByClient(any(Client.class), any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Address addressToUpdate = new Address(null, "san pancho", "chalco", "estado de mexico", 301245L);
        
        // When
        MvcResult result = mockMvc.perform(put("/api/clients/" + idClientToSearch + "/address")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(addressToUpdate)))
        
        // Then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Alejandro"))
            .andExpect(jsonPath("$.lastname").value("Granados"))
            .andExpect(jsonPath("$.email").value("alejandro.magb@gmail.com"))
            .andExpect(jsonPath("$.phonenumber").value(1538977020L))
            .andReturn()
        ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Client client = objectMapper.readValue(jsonString, Client.class);

        assertNotNull(client);
        assertEquals(1L, client.getId());
        assertEquals("Alejandro", client.getName());
        assertEquals("Granados", client.getLastname());
        assertEquals("alejandro.magb@gmail.com", client.getEmail());
        assertEquals(1538977020L, client.getPhonenumber());

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(service).editAddressByClient(any(Client.class), any(Address.class));
    }

    // To test the endpoint editAddressByClientId when the idClient doesnt exist
    @Test
    void putEditAddressByClientIdInexistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 999999L;
        when(clientService.findById(anyLong())).thenReturn(Optional.empty());
        Address addressToUpdate = new Address(null, "san pancho", "chalco", "estado de mexico", 301245L);
        
        // When
        mockMvc.perform(put("/api/clients/" + idClientToSearch + "/address")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(addressToUpdate)))
        
        // Then
        .andExpect(status().isNotFound())
        .andExpect(content().string(""))
        ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
    }

    // To test the endpoint deleteAddressByClientId when we use an existing id 
    @Test
    void deleteAddressByClientIdExistingIdTest() throws Exception {
    
        // Given
        Long idClientToSearch = 1L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient001()));
        when(service.deleteAddressByClient(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        MvcResult result = mockMvc.perform(delete("/api/clients/" + idClientToSearch + "/address"))
        
        // Then
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Alejandro"))
            .andExpect(jsonPath("$.lastname").value("Granados"))
            .andExpect(jsonPath("$.email").value("alejandro.magb@gmail.com"))
            .andExpect(jsonPath("$.phonenumber").value(1538977020L))
            .andReturn()
        ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Client client = objectMapper.readValue(jsonString, Client.class);

        assertNotNull(client);
        assertEquals(1L, client.getId());
        assertEquals("Alejandro", client.getName());
        assertEquals("Granados", client.getLastname());
        assertEquals("alejandro.magb@gmail.com", client.getEmail());
        assertEquals(1538977020L, client.getPhonenumber());

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
        verify(service).deleteAddressByClient(any(Client.class));
    }

    // To test the endpoint deleteAddressByClientId when we use an inexisting id 
    @Test
    void deleteAddressByClientIdInexistingIdTest() throws Exception {
    
        // Given
        Long idClientToSearch = 999999L;
        when(clientService.findById(anyLong())).thenReturn(Optional.empty());
        
        // When
        mockMvc.perform(delete("/api/clients/" + idClientToSearch + "/address")
            .contentType(MediaType.APPLICATION_JSON))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
            ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
    }

    // To test the method validation
    @Test
    void validationTest() throws Exception {

        // Given
        Long idClientToSearch = 3L;
        Address addressInsert = new Address(null, "", "", "", null);
        
        // when
        mockMvc.perform(post("/api/clients/" + idClientToSearch + "/address")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(addressInsert)))
        
        // then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.street").value("El campo street must not be blank"))
            .andExpect(jsonPath("$.state").value("El campo state must not be blank"))
            .andExpect(jsonPath("$.city").value("El campo city must not be blank"))
            .andExpect(jsonPath("$.cp").value("El campo cp must not be null"))
            ;

        verify(service, never()).saveAddressByClient(any(Client.class), any(Address.class));
    }

}
