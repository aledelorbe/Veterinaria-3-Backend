// package com.alejandro.veterinaria.services;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyLong;
// import static org.mockito.ArgumentMatchers.argThat;
// import static org.mockito.Mockito.*;

// import java.util.List;
// import java.util.NoSuchElementException;
// import java.util.Optional;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import com.alejandro.veterinaria.data.PetData;
// import com.alejandro.veterinaria.data.ClientData;
// import com.alejandro.veterinaria.data.CustomCondition;
// import com.alejandro.veterinaria.entities.Client;
// import com.alejandro.veterinaria.entities.Pet;
// import com.alejandro.veterinaria.repositories.ClientRepository;
// import com.alejandro.veterinaria.repositories.PetRepository;

// @ExtendWith(MockitoExtension.class)
// class PetServiceImpTest {
    
//     // To create a mock
//     @Mock
//     PetRepository repository; 

//     // To create a mock
//     @Mock
//     ClientRepository clientRepository; 

//     // To create a service object with the injection of a mock
//     @InjectMocks
//     PetServiceImp service;

//     // To create a service object with the injection of a mock
//     @InjectMocks
//     ClientServiceImp clientService;

//     // To test the method findById when we use an existing id
//     @Test
//     void findByIdExistingIdTest() {

//         // Given
//         Long idToSearch = 50L;
//         when(repository.findById(anyLong())).thenReturn(Optional.of(PetData.createPet005()));

//         // when
//         Optional<Pet> optionalPet = service.findById(idToSearch);

//         // then
//         assertNotNull(optionalPet.get());
//         assertEquals(50L, optionalPet.get().getId());
//         assertEquals("nala", optionalPet.get().getName());
//         assertEquals("perro", optionalPet.get().getSpecie());
//         assertNull(optionalPet.get().getBreed());
//         assertEquals(4L, optionalPet.get().getAge());
//         assertEquals("no quiere comer", optionalPet.get().getReasonForVisit());
        
//         verify(repository).findById(argThat(new CustomCondition(PetData.idsValid, true)));
//     }

//     // To test the method findById when we use an inexisting id
//     @Test
//     void findByIdInexistingIdTest() {

//         // Given
//         Long idToSearch = 11L;
//         when(repository.findById(anyLong())).thenReturn(Optional.empty());

//         // when
//         Optional<Pet> optionalPet2 = service.findById(idToSearch);

//         // then
//         assertFalse(optionalPet2.isPresent());
//         assertThrows(NoSuchElementException.class, () -> {
//             optionalPet2.orElseThrow();
//         });

//         verify(repository).findById(argThat(new CustomCondition(PetData.idsValid, false)));
//     }

//     // To test the method getPetsByClient
//     @Test
//     void getPetsByClientTest() {
        
//         // Given
//         Client clientDb = ClientData.createClient004();

//         // when
//         List<Pet> pets = service.getPetsByClient(clientDb);

//         // then
//         assertNotNull(pets);
//         assertEquals(2, pets.size());
//         assertEquals(80L, pets.get(1).getId());
//         assertEquals("quick", pets.get(1).getName());
//         assertEquals("perro", pets.get(1).getSpecie());
//         assertEquals("labrador", pets.get(1).getBreed());
//         assertEquals(10L, pets.get(1).getAge());
//         assertEquals("tiene mucho sueño", pets.get(1).getReasonForVisit());
//     }

//     // To test the method savePetByClient
//     @Test
//     void savePetByClientTest() {
    
//         // Given
//         Client clientDb = ClientData.createClient004();
//         Pet petInsert = new Pet(null, "lince intergalactico", "lince", null, 5L, "le duele el estomago");
//         when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
//         // when
//         Client newClient = service.savePetByClient(clientDb, petInsert);
        
//         // then
//         int size = newClient.getPets().size();
//         List<Pet> pets = newClient.getPets();

//         assertNotNull(newClient);
//         assertEquals(4L, newClient.getId());
//         assertEquals("Esteban", newClient.getName());
//         assertEquals("Gonzalez", newClient.getLastname());
//         assertEquals("pastor34@idoidraw.com", newClient.getEmail());
//         assertEquals(1234567890L, newClient.getPhonenumber());

//         assertNotNull(pets);
//         assertEquals(3, size);
//         assertEquals("lince intergalactico", pets.get(size - 1).getName());
//         assertEquals("lince", pets.get(size - 1).getSpecie());
//         assertNull(pets.get(size - 1).getBreed());
//         assertEquals(5L, pets.get(size - 1).getAge());
//         assertEquals("le duele el estomago", pets.get(size - 1).getReasonForVisit());

//         verify(clientRepository).save(any(Client.class));
//     }

//     // To test the method editPetByClient when we use an existing id
//     @Test
//     void editPetByClientExistingIdTest() {
        
//         // Given
//         Client clientDb = ClientData.createClient004();
//         Long idToSearch = 80L;
//         Pet petToUpdate = new Pet(null, "lince update", "reptil", null, 3L, "infeccion en los ojos");
//         when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

//         // When
//         Optional<Client> optionalClient = service.editPetByClient(clientDb, idToSearch, petToUpdate);

//         // then
//         Client newClientDb = optionalClient.get();

//         assertNotNull(newClientDb);
//         assertEquals(4L, newClientDb.getId());
//         assertEquals("Esteban", newClientDb.getName());
//         assertEquals("Gonzalez", newClientDb.getLastname());
//         assertEquals("pastor34@idoidraw.com", newClientDb.getEmail());
//         assertEquals(1234567890L, newClientDb.getPhonenumber());

//         Pet petUpdated = newClientDb.getPets().get(1);

//         assertEquals("lince update", petUpdated.getName());
//         assertEquals("reptil", petUpdated.getSpecie());
//         assertNull(petUpdated.getBreed());
//         assertEquals(3L, petUpdated.getAge());
//         assertEquals("infeccion en los ojos", petUpdated.getReasonForVisit());

//         verify(clientRepository).save(any(Client.class));
//     }

//     // To test the method editPetByClient when we use an inexisting id
//     @Test
//     void editPetByClientInexistingIdTest() {
        
//         // Given
//         Long idToSearch = 9999L;
//         Client clientDb = ClientData.createClient004();
//         Pet petToUpdate = new Pet(null, "lince update", "reptil", null, 3L, "infeccion en los ojos");
        
//         // When
//         Optional<Client> optionalClient = service.editPetByClient(clientDb, idToSearch, petToUpdate);

//         // Then
//         assertFalse(optionalClient.isPresent());
//         assertThrows(NoSuchElementException.class, () -> {
//             optionalClient.orElseThrow();
//         });

//         verify(clientRepository, never()).save(any(Client.class));
//     }

//     // To test the method deletePetByClient when we use an existing id
//     @Test
//     void deletePetByClientExistingIdTest() {
        
//         // Given
//         Client clientDb = ClientData.createClient004();
//         Long petIdToSearch = 80L;
//         when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> invocation.getArgument(0));

//         // when: get pets (first time)
//         List<Pet> pets = service.getPetsByClient(clientDb);

//         // then
//         assertNotNull(pets);
//         assertEquals(2, pets.size());

//         // when: Delete a pet and get pets (first time)
//         Optional<Client> optionalClient2 = service.deletePetByClient(clientDb, petIdToSearch);
//         List<Pet> pets2 = service.getPetsByClient(optionalClient2.get());

//         // then
//         assertNotNull(pets2);
//         assertEquals(1, pets2.size());

//         verify(clientRepository).save(any(Client.class));
//     }

//     // To test the method deletePetByClient when we use an inexisting id
//     @Test
//     void deletePetByClientInexistingIdTest() {
        
//         // Given
//         Client clientDb = ClientData.createClient004();
//         Long petIdToSearch = 999999L;

//         // when: get pets (first time)
//         List<Pet> pets = service.getPetsByClient(clientDb);

//         // then
//         assertNotNull(pets);
//         assertEquals(2, pets.size());

//         // when: delete
//         Optional<Client> optionalClient2 = service.deletePetByClient(clientDb, petIdToSearch);

//         // then
//         assertFalse(optionalClient2.isPresent());
//         assertThrows(NoSuchElementException.class, () -> {
//             optionalClient2.orElseThrow();
//         });

//         // when: get pets (Second time)
//         List<Pet> pets2 = service.getPetsByClient(clientDb);

//         // then
//         assertNotNull(pets2);
//         assertEquals(2, pets2.size());

//         verify(clientRepository, never()).save(any(Client.class));
//     }

// }
