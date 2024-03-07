package fr.univcotedazur.isadevops.dto;

import jakarta.validation.constraints.NotBlank;

public record PartnerDTO (
    Long id,
    @NotBlank String name,
    @NotBlank(message = "location should not be blank") String location,
    String description){

}
