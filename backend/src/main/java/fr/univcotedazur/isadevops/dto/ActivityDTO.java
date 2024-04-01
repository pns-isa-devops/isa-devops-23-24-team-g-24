package fr.univcotedazur.isadevops.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record ActivityDTO (
        Long id,
        @NotBlank(message = "name should not be blank")
        String name,
        @NotBlank(message = "location should not be blank")
        String location,
        @NotNull
        Long numberOfPlaces,
        @NotNull
        Long pointsEarned,
        @NotNull
        double price,
        @NotNull
        Long pricePoints,
        Long idPartner
        ){
}
