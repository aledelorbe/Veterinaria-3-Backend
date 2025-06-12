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

    // To test the 'getAddressByClient' endpoint when the client has an address
    @Test
    void getAddressByClientExistingIdTest() throws Exception {

        // Given
        Long idClientToSearch = 2L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient002()));

        // When
        MvcResult result = mockMvc.perform(get("/api/clients/" + idClientToSearch + "/addresses"))

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
    }

    // To test the 'getAddressByClient' endpoint when the client does not have an address
    @Test
    void getAddressByClientExistingIdNoAddressTest() throws Exception {

        // Given
        Long idClientToSearch = 5L;
        when(clientService.findById(anyLong())).thenReturn(Optional.of(ClientData.createClient005()));

        // When
        mockMvc.perform(get("/api/clients/" + idClientToSearch + "/addresses"))

        // Then
            .andExpect(status().isOk())
            .andExpect(content().string(""))
        ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, true)));
    }
    
    // To test the 'getAddressByClient' endpoint when the client does not exist
    @Test
    void getAddressByClientInexistingIdTest() throws Exception {
        
        // Given
        Long idClientToSearch = 999999L;
        when(clientService.findById(anyLong())).thenReturn(Optional.empty());
        
        // When
        mockMvc.perform(get("/api/clients/" + idClientToSearch + "/addresses"))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(clientService).findById(argThat(new CustomCondition(ClientData.idsValid, false)));
    }

    // To test the 'saveNewAddressByClientId' endpoint when the address can be saved
    @Test
    void postSaveNewAddressByClientIdSuccessSaveTest() throws Exception {

        // Given
        Long idClientToSearch = 5L;
        when(service.saveAddressByClient(anyLong(), any(Address.class))).thenReturn(Optional.of(ClientData.createClient004()));
        Address addressToInsert = new Address(null, "independencia", "huchapan", "hidalgo", 45555L);
        
        // When
        MvcResult result = mockMvc.perform(post("/api/clients/" + idClientToSearch + "/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(addressToInsert)))

        // Then
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$").isNotEmpty())
            .andExpect(jsonPath("$.id").value(4L))
            .andExpect(jsonPath("$.name").value("Esteban"))
            .andExpect(jsonPath("$.lastname").value("Gonzalez"))
            .andExpect(jsonPath("$.email").value("pastor34@idoidraw.com"))
            .andExpect(jsonPath("$.phonenumber").value(1234567890L))
            .andReturn()
        ;

        // Convert the response to an object
        String jsonString = result.getResponse().getContentAsString();
        Client client = objectMapper.readValue(jsonString, Client.class);

        assertNotNull(client);
        assertEquals(4L, client.getId());
        assertEquals("Esteban", client.getName());
        assertEquals("Gonzalez", client.getLastname());
        assertEquals("pastor34@idoidraw.com", client.getEmail());
        assertEquals(1234567890L, client.getPhonenumber());

        verify(service).saveAddressByClient(argThat(new CustomCondition(ClientData.idsValid, true)), any(Address.class));
    }

    // To test the 'saveNewAddressByClientId' endpoint when the address can not be saved
    @Test
    void postSaveNewAddressByClientIdUnsuccessSaveTest() throws Exception {

        // Given
        Long idClientToSearch = 999999L;
        when(service.saveAddressByClient(anyLong(), any(Address.class))).thenReturn(Optional.empty());
        Address addressToInsert = new Address(null, "independencia", "huchapan", "hidalgo", 45555L);
        
        // When
        mockMvc.perform(post("/api/clients/" + idClientToSearch + "/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(addressToInsert)))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(service).saveAddressByClient(argThat(new CustomCondition(ClientData.idsValid, false)), any(Address.class));
    }

    // To test the 'editAddressByClientId' endpoint when the address can be updated
    @Test
    void putEditAddressByClientIdSuccessUpdateTest() throws Exception {

        // Given
        Long idClientToSearch = 1L;
        when(service.editAddressByClient(anyLong(), any(Address.class))).thenReturn(Optional.of(ClientData.createClient001()));
        Address addressToUpdate = new Address(null, "san pancho", "chalco", "estado de mexico", 301245L);
        
        // When
        MvcResult result = mockMvc.perform(put("/api/clients/" + idClientToSearch + "/addresses")
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

        verify(service).editAddressByClient(argThat(new CustomCondition(ClientData.idsValid, true)), any(Address.class));
    }

    // To test the 'editAddressByClientId' endpoint when the address can not be updated
    @Test
    void putEditAddressByClientIdUnsuccessUpdateTest() throws Exception {

        // Given
        Long idClientToSearch = 999999L;
        when(service.editAddressByClient(anyLong(), any(Address.class))).thenReturn(Optional.empty());
        Address addressToUpdate = new Address(null, "san pancho", "chalco", "estado de mexico", 301245L);
        
        // When
        mockMvc.perform(put("/api/clients/" + idClientToSearch + "/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(addressToUpdate)))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(service).editAddressByClient(argThat(new CustomCondition(ClientData.idsValid, false)), any(Address.class));
    }

    // To test the 'deleteAddressByClientId' endpoint when the address can be deleted
    @Test
    void deleteAddressByClientIdSuccessDeleteTest() throws Exception {
    
        // Given
        Long idClientToSearch = 1L;
        when(service.deleteAddressByClient(anyLong())).thenReturn(Optional.of(ClientData.createClient001()));

        // When
        MvcResult result = mockMvc.perform(delete("/api/clients/" + idClientToSearch + "/addresses"))
        
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

        verify(service).deleteAddressByClient(argThat(new CustomCondition(ClientData.idsValid, true)));
    }

    // To test the 'deleteAddressByClientId' endpoint when the address can not be deleted
    @Test
    void deleteAddressByClientIdUnsuccessDeleteTest() throws Exception {
    
        // Given
        Long idClientToSearch = 999999L;
        when(service.deleteAddressByClient(anyLong())).thenReturn(Optional.empty());
        
        // When
        mockMvc.perform(delete("/api/clients/" + idClientToSearch + "/addresses")
            .contentType(MediaType.APPLICATION_JSON))
        
        // Then
            .andExpect(status().isNotFound())
            .andExpect(content().string(""))
        ;

        verify(service).deleteAddressByClient(argThat(new CustomCondition(ClientData.idsValid, false)));
    }

    // To test the method validation
    @Test
    void validationTest() throws Exception {

        // Given
        Long idClientToSearch = 3L;
        Address addressInsert = new Address(null, "", "", "", null);
        
        // when
        mockMvc.perform(post("/api/clients/" + idClientToSearch + "/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(addressInsert)))
        
        // then
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.street").value("El campo street must not be blank"))
            .andExpect(jsonPath("$.state").value("El campo state must not be blank"))
            .andExpect(jsonPath("$.city").value("El campo city must not be blank"))
            .andExpect(jsonPath("$.cp").value("El campo cp must not be null"))
        ;

        verify(service, never()).saveAddressByClient(anyLong(), any(Address.class));
    }

}
