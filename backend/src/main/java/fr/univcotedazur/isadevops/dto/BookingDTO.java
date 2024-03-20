package fr.univcotedazur.isadevops.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookingDTO (
        Long id,
        @NotNull(message = "CustomerId should not be blank") Long customerId,
        @NotNull(message = "ActivityId should not be blank") Long activityId,
        @NotNull(message = "UsePoints should not be blank") boolean usePoints){
}
