package fr.univcotedazur.isadevops.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

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
    @NotNull
    private double price;
    @NotNull
    private Long pricePoints;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    public Activity() {
    }

    
    public Activity(String name, String location, Long numberOfPlaces,double price, Long pricePoints) {
        this.name = name;
        this.location = location;
        this.numberOfPlaces = numberOfPlaces;
        this.price = price;
        this.pricePoints = pricePoints;
    }


    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Partner getPartner() {
        return partner;
    }


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

    public Long getPricePoints() {
        return pricePoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Activity activity)) return false;
        return Objects.equals(name, activity.name) && Objects.equals(location, activity.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location);
    }

    public void setId(long l) {
        this.id = l;
    }
}
