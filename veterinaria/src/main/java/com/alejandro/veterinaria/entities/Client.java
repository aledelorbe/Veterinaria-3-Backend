package com.alejandro.veterinaria.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// To specific the name of the table in mysql
// In mysql the name of this table is 'client' but in this project 
// the name of this class is 'Client'
@Entity
@Table(name = "client") 
public class Client {

    // Mapping of class attributes with table fields in mysql

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank // To obligate to this attribute not to empty or blank values.
    private String name;

    @NotBlank // To obligate to this attribute not to empty or blank values.
    private String lastname;
    
    @NotBlank // To obligate to this attribute not to empty or blank values.
    @Email
    private String email;

    // To obligate this attribute to contain values ​​equal to or greater than 1000000000
    // This ensures that this attribute will contain more than 10 digits.
    @Min(value = 1000000000, message = "{Min.client.phonenumber}")
    @Max(value = 9999999999L, message = "{Max.client.phonenumber}")
    @NotNull // To obligate to this attribute not to empty
    private Long phonenumber;

    // To set a relationship one to many
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_client")
    private Set<Pet> pets;

    public Client() {
        this.pets = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Long phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }
}
