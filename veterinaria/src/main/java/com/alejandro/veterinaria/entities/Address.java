package com.alejandro.veterinaria.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// To specific the name of the table in mysql
// In mysql the name of this table is 'address' but in this project 
// the name of this class is 'Address'
@Entity
@Table(name = "address") 
public class Address {
    // Mapping of class attributes with table fields in mysql

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address")
    private Long id;

    @NotBlank // To obligate to this attribute not to empty or blank values.
    private String street;

    @NotBlank // To obligate to this attribute not to empty or blank values.
    private String state;

    @NotBlank // To obligate to this attribute not to empty or blank values.
    private String city;
    
    @NotNull // To obligate to this attribute not to empty
    private Long cp;

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCp() {
        return cp;
    }

    public void setCp(Long cp) {
        this.cp = cp;
    }
}
