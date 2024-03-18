package fr.univcotedazur.isadevops.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record ActivityDTO (
        Long id,
        @NotBlank(message = "name should not be blank") String name,
        @NotBlank(message = "location should not be blank") String location,
        Long numberOfPlaces,
        Long pointsEarned,
        @NotNull
        double price,
        Long pricePoints
        ){
}
