package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Booking;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.ActivityIdNotFoundException;
import fr.univcotedazur.isadevops.exceptions.CustomerIdNotFoundException;
import fr.univcotedazur.isadevops.exceptions.PaymentException;

public interface BookingCreator {

    public Booking createBooking(Long customerId, Long activityId) throws PaymentException, CustomerIdNotFoundException, ActivityIdNotFoundException;
    public boolean cancelBooking(Long bookingId);
}
