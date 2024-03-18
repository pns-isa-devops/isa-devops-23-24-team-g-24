package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Booking;

import java.util.List;
import java.util.Optional;


public interface BookingFinder {

    List<Booking> findAllBookings();
    List<Booking> findBookingsByCustomerId(Long customerId);
    List<Booking> findBookingsByActivityId(Long activityId);
}
