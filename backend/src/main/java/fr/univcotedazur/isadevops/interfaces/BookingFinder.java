package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Booking;

import java.util.List;
import java.util.Optional;


public interface BookingFinder {

    Optional<Booking> findById(Long id);
    List<Booking> findByCustomerId(Long customerId);
}
