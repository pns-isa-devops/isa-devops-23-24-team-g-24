package fr.univcotedazur.isadevops.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CustomerDTO (
    Long id,
    @NotBlank(message = "name should not be blank") String name,
    @Pattern(regexp = "\\d{10}+", message = "credit card should be exactly 10 digits") String creditCard,
    double pointsBalance,
    Long groupId){
}
