package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Booking;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.*;

public interface BookingCreator {

    public Booking createBooking(Long customerId, Long activityId, boolean usePoints) throws PaymentException, CustomerIdNotFoundException, ActivityIdNotFoundException, NotEnoughPointsException, NotEnoughPlacesException;
    public boolean cancelBooking(Long bookingId);
}
