package fr.univcotedazur.isadevops.dto;

import jakarta.validation.constraints.NotBlank;

public record ActivityDTO (
        Long id,
        @NotBlank(message = "name should not be blank") String name,
        @NotBlank(message = "location should not be blank") String location,
        Long numberOfPlaces,
        Long idPartner
)
{
    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String location() {
        return location;
    }

    public Long numberOfPlaces() {
        return numberOfPlaces;
    }

    public Long idPartner() {
        return idPartner;
    }
}
