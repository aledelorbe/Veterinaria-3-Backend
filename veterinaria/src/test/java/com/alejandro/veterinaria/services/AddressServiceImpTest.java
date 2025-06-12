// package com.alejandro.veterinaria.services;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// import java.util.Optional;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import com.alejandro.veterinaria.data.ClientData;
// import com.alejandro.veterinaria.entities.Address;
// import com.alejandro.veterinaria.entities.Client;
// import com.alejandro.veterinaria.repositories.ClientRepository;

// @ExtendWith(MockitoExtension.class)
// class AddressServiceImpTest {
    
//     // To create a mock
//     @Mock
//     ClientRepository clientRepository; 

//     // To create a service object with the injection of a mock
//     @InjectMocks
//     AddressServiceImp service;

//     // To create a service object with the injection of a mock
//     @InjectMocks
//     ClientServiceImp clientService;

//     // To test the method getAddressByClient when the client has an address
//     @Test
//     void getAddressByClientHasAddressTest() {
    
//         // Given
//         Client clientDb = ClientData.createClient004();

//         // when
//         Optional<Address> optionalAddress = service.getAddressByClient(clientDb);
//         Address address = optionalAddress.get();

//         // Then
//         assertNotNull(clientDb);
//         assertEquals(4L, clientDb.getId());
//         assertEquals("Esteban", clientDb.getName());
//         assertEquals("Gonzalez", clientDb.getLastname());
//         assertEquals("pastor34@idoidraw.com", clientDb.getEmail());
//         assertEquals(1234567890L, clientDb.getPhonenumber());

//         assertNotNull(address);
//         assertEquals(400L, address.getId());
//         assertEquals("candelaria", address.getStreet());
//         assertEquals("gustavo madero", address.getState());
//         assertEquals("cdmx", address.getCity());
//         assertEquals(56546L, address.getCp());
//     }

//     // To test the method getAddressByClient when the client does not have an address
//     @Test
//     void getAddressByClientNoAddressTest() {
    
//         // Given
//         Client clientDb = ClientData.createClient005();

//         // when
//         Optional<Address> optionalAddress = service.getAddressByClient(clientDb);

//         // Then
//         assertFalse(optionalAddress.isPresent());
//     }

//     // To test the method saveAddressByClient 
//     @Test
//     void saveAddressByClientTest() {
    
//         // Given
//         Client clientDb = ClientData.createClient005();
//         Address addressInsert = new Address(null, "gomez farias", "chilpancingo", "guerrero", 56126L);
//         when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
//         // when
//         Client newClient = service.saveAddressByClient(clientDb, addressInsert);
//         Address newAddress = newClient.getAddress();
        
//         // then
//         assertEquals("John", newClient.getName());
//         assertEquals("Lennon", newClient.getLastname());
//         assertEquals("lennon@idoidraw.com", newClient.getEmail());
//         assertEquals(45208954L, newClient.getPhonenumber());

//         assertEquals("gomez farias", newAddress.getStreet());
//         assertEquals("chilpancingo", newAddress.getState());
//         assertEquals("guerrero", newAddress.getCity());
//         assertEquals(56126L, newAddress.getCp());

//         verify(clientRepository).save(any(Client.class));
//     }

//     // To test the method editAddressByClient 
//     @Test
//     void editAddressByClientTest() {
        
//         // Given
//         Client clientDb = ClientData.createClient004();
//         Address addressToUpdate = new Address(null, "miguel hidalgo", "acatlan", "puebla", 56346L);
//         when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

//         // When
//         Client newClientDb = service.editAddressByClient(clientDb, addressToUpdate);
//         Address addressUpdated = newClientDb.getAddress();

//         // then
//         assertNotNull(newClientDb);
//         assertEquals(4L, newClientDb.getId());
//         assertEquals("Esteban", newClientDb.getName());
//         assertEquals("Gonzalez", newClientDb.getLastname());
//         assertEquals("pastor34@idoidraw.com", newClientDb.getEmail());
//         assertEquals(1234567890L, newClientDb.getPhonenumber());

//         assertEquals("miguel hidalgo", addressUpdated.getStreet());
//         assertEquals("acatlan", addressUpdated.getState());
//         assertEquals("puebla", addressUpdated.getCity());
//         assertEquals(56346L, addressUpdated.getCp());

//         verify(clientRepository).save(any(Client.class));
//     }

//     // To test the method deleteAddressByClient
//     @Test
//     void deleteAddressByClientTest() {
    
//         // Given
//         Client clientDb = ClientData.createClient003();
//         when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

//         // When
//         Client newClientDb = service.deleteAddressByClient(clientDb);

//         // then
//         assertNotNull(newClientDb);
//         assertEquals(3L, newClientDb.getId());
//         assertEquals("Celia", newClientDb.getName());
//         assertEquals("Bello", newClientDb.getLastname());
//         assertEquals("cazador19@idoidraw.com", newClientDb.getEmail());
//         assertEquals(1234977026L, newClientDb.getPhonenumber());

//         assertNull(newClientDb.getAddress());

//         verify(clientRepository).save(any(Client.class));
//     }

// }
