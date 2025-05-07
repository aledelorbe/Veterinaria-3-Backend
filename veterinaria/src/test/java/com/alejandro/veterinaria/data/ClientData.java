package com.alejandro.veterinaria.data;

import java.util.Arrays;
import java.util.List;

import com.alejandro.veterinaria.entities.Client;

// The class that contains the data to be mocked in the service and controller methods
public class ClientData {
    
    public static final List<Long> idsValid = Arrays.asList(1L, 2L, 3L, 4L, 5L);

    public static Client createClient001() {
        return new Client(idsValid.get(0), "Alejandro", "Granados", "alejandro.magb@gmail.com", 1538977020L, PetData.createPets001(), AddressData.createAddress001());
    }
    
    public static Client createClient002() {
        return new Client(idsValid.get(1), "Hueto", "Navejas", "hekevim148@idoidraw.com", 1538971230L, PetData.createPets002(), AddressData.createAddress002());
    }

    public static Client createClient003() {
        return new Client(idsValid.get(2), "Celia", "Bello", "cazador19@idoidraw.com", 1234977026L, PetData.createPets003(), AddressData.createAddress003());
    }

    public static Client createClient004() {
        return new Client(idsValid.get(3), "Esteban", "Gonzalez", "pastor34@idoidraw.com", 1234567890L, PetData.createPets004(), AddressData.createAddress004());
    }

    public static Client createClient005() {
        return new Client(idsValid.get(4), "John", "Lennon", "lennon@idoidraw.com", 45208954L, null, null);
    }

    public static List<Client> createClients001() {
        return Arrays.asList(createClient001(), createClient002(), createClient003(), createClient004(), createClient005());
    }

}



