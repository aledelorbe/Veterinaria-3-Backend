package com.alejandro.veterinaria.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.alejandro.veterinaria.entities.Pet;

// The class that contains the data to be mocked in the service and controller methods
public class PetData {
    
    public static final List<Long> idsValid = Arrays.asList(10L, 20L, 30L, 40L, 50L, 60L, 70L, 80L, 90L);

    public static Pet createPet001() {
        return new Pet(idsValid.get(0), "rayas", "gato", "rayado", 11L, "tiene mucho sueño");
    }
    
    public static Pet createPet002() {
        return new Pet(idsValid.get(1), "goliath", "perro", "chihuahua", 4L, "vomita mucho");
    }

    public static Pet createPet003() {
        return new Pet(idsValid.get(2), "baguira", "perro", "pastor aleman", 15L, "le duele el estomago");
    }

    public static Pet createPet005() {
        return new Pet(idsValid.get(4), "nala", "perro", null, 4L, "no quiere comer");
    }

    public static Pet createPet006() {
        return new Pet(idsValid.get(5), "pennywise", "gato", "manchado", 5L, "no puede ir al baño");
    }
    
    public static Pet createPet007() {
        return new Pet(idsValid.get(6), "goliath", "perro", "chihuahua", 4L, "tiene mucho sueño");
    }

    public static Pet createPet008() {
        return new Pet(idsValid.get(7), "quick", "perro", "labrador", 10L, "tiene mucho sueño");
    }

    public static Pet createPet009() {
        return new Pet(idsValid.get(8), "guana", "gato", null, 7L, "esta emarazada");
    }

    public static List<Pet> createPets001() {
        return new ArrayList<>(Arrays.asList(createPet001()));
    }

    public static List<Pet> createPets002() {
        return new ArrayList<>(Arrays.asList(createPet002(), createPet003(), createPet009()));
    }

    public static List<Pet> createPets003() {
        return new ArrayList<>(Arrays.asList(createPet005(), createPet006()));
    }

    public static List<Pet> createPets004() {
        return new ArrayList<>(Arrays.asList(createPet007(), createPet008()));
    }

}
