package fr.univcotedazur.isadevops.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotNull
    private Long numberOfPlaces;

    public Activity() {
    }
    public Activity(String name, String location, Long numberOfPlaces) {
        this.name = name;
        this.location = location;
        this.numberOfPlaces = numberOfPlaces;
    }

    @NotNull
    private double price;

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String localisation) {
        this.location = localisation;
    }

    public Long getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(Long numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
