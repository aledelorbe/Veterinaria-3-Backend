package com.alejandro.veterinaria.data;

import java.util.Arrays;
import java.util.List;

import com.alejandro.veterinaria.entities.Address;

// The class that contains the data to be mocked in the service and controller methods
public class AddressData {
    
    public static final List<Long> idsValid = Arrays.asList(100L, 200L, 300L, 400L);

    public static Address createAddress001() {
        return new Address(idsValid.get(0), "ignacio zaragoza", "ixtapaluca", "estado de mexico", 56585L);
    }
    
    public static Address createAddress002() {
        return new Address(idsValid.get(1), "av. siempre viva", "venustiano carranza", "cdmx", 56512L);
    }

    public static Address createAddress003() {
        return new Address(idsValid.get(2), "calle false 123", "iztapalapa", "cdmx", 12585L);
    }

    public static Address createAddress004() {
        return new Address(idsValid.get(3), "candelaria", "gustavo madero", "cdmx", 56546L);
    }

}
