package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Booking;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.ActivityIdNotFoundException;
import fr.univcotedazur.isadevops.exceptions.CustomerIdNotFoundException;
import fr.univcotedazur.isadevops.exceptions.PaymentException;
import fr.univcotedazur.isadevops.interfaces.*;
import fr.univcotedazur.isadevops.repositories.ActivityRepository;
import fr.univcotedazur.isadevops.repositories.BookingRepository;
import fr.univcotedazur.isadevops.repositories.CustomerRepository;
import fr.univcotedazur.isadevops.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookingHandler implements BookingCreator, BookingFinder {

    private final Payment payment;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final ActivityRepository activityRepository;
    @Autowired
    CustomerFinder customerFinder;

    @Autowired
    ActivityService activityService;
    @Autowired
    public BookingHandler(BookingRepository bookingRepository, CustomerRepository customerRepository, ActivityRepository activityRepository, Payment payment){
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.activityRepository = activityRepository;
        this.payment = payment;
    }

    @Override
    @Transactional
    public Booking createBooking(Long customerId, Long activityId) throws CustomerIdNotFoundException, ActivityIdNotFoundException {
        System.out.println("CustomerID: " + customerId);
        System.out.println("ActivityID: " + activityId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerIdNotFoundException());
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityIdNotFoundException());

        // Il faut check si l'activité a assez de places

        Booking booking = new Booking(customer, activity);
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public boolean cancelBooking(Long bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            return false;
        }
        bookingRepository.deleteById(bookingId);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findBookingsByCustomerId(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Booking> findBookingsByActivityId(Long activityId) {
        return bookingRepository.findByActivityId(activityId);
    }


}
