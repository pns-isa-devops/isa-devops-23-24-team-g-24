package fr.univcotedazur.isadevops.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.List;

@Entity
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    private String description;


    public Partner() {
    }

    public Partner(String name, String location, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(long l) {
        this.id = l;
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
    public void setLocation(String location) {
        this.location = location;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partner partner = (Partner) o;
        return id.equals(partner.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location, description);
    }
}
