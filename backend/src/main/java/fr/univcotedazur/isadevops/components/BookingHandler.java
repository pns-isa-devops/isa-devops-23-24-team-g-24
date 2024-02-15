package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Booking;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.PaymentException;
import fr.univcotedazur.isadevops.interfaces.Bank;
import fr.univcotedazur.isadevops.interfaces.BookingCreator;
import fr.univcotedazur.isadevops.interfaces.BookingFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import fr.univcotedazur.isadevops.interfaces.Payment;

import java.util.List;
import java.util.Optional;

@Service
public class BookingHandler implements BookingCreator, BookingFinder {

    Payment payment;

    @Autowired
    public BookingHandler(Payment payment) {
        this.payment = payment;
    }








    @Override
    public Booking createBooking(Customer customer, Activity activity) throws PaymentException {
        // Checks whether the customer can pay for the activity
        payment.pay(activity.getPrice(), customer);

        return new Booking(customer, activity);
    }

    @Override
    public Optional<Booking> findById(Long id) {
        //TODO
        return Optional.empty();
    }

    @Override
    public List<Booking> findByCustomerId(Long customerId) {
        //TODO
        return null;
    }
}
