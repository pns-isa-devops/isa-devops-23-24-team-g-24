package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Booking;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.PaymentException;

public interface BookingCreator {

    Booking createBooking(Customer customer, Activity activity) throws PaymentException;

}
