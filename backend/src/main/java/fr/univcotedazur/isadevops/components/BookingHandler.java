package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Booking;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.*;
import fr.univcotedazur.isadevops.interfaces.*;
import fr.univcotedazur.isadevops.repositories.ActivityRepository;
import fr.univcotedazur.isadevops.repositories.BookingRepository;
import fr.univcotedazur.isadevops.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class BookingHandler implements BookingCreator, BookingFinder {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final ActivityRepository activityRepository;
    CustomerFinder customerFinder;
    ActivityCreator activityService;
    private final Scheduler scheduler;
    private final Payment payment;
    private static final Logger LOG = LoggerFactory.getLogger(BookingHandler.class);

    @Autowired
    public BookingHandler(
                          BookingRepository bookingRepository,
                          CustomerRepository customerRepository,
                          ActivityRepository activityRepository,
                          CustomerFinder customerFinder,
                          ActivityCreator activityCreator,
                          Scheduler scheduler,
                          Payment payment
                          ){

        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.activityRepository = activityRepository;
        this.customerFinder = customerFinder;
        this.activityService = activityCreator;
        this.scheduler = scheduler;
        this.payment = payment;
    }

    @Override
    @Transactional
    public Booking createBooking(Long customerId, Long activityId, boolean usePoints) throws CustomerIdNotFoundException, ActivityIdNotFoundException, PaymentException, NotEnoughPointsException, NotEnoughPlacesException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerIdNotFoundException(customerId));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityIdNotFoundException(activityId));
        if(bookingRepository.findByActivityId(activityId).size()>=activity.getNumberOfPlaces()){
            throw new NotEnoughPlacesException();
        }
        if (usePoints) {
            if (customer.getPointsBalance() < activity.getPricePoints()) {
                throw new NotEnoughPointsException();
            }
            customer.setPointsBalance(customer.getPointsBalance() - activity.getPricePoints());
        } else {
            payment.pay(activity.getPrice(), customer);
        }
        customer.setPointsBalance(customer.getPointsBalance() + activity.getPointEarned());
        customerRepository.save(customer);

        Booking booking = new Booking(customer, activity, usePoints);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString = currentDate.format(formatter);

        Optional<String> resultPost = this.scheduler.book(dateString, activity.getName(), "magicPartner");
        if(resultPost.isEmpty()){
            LOG.info("Resultat vide de la part du scheduler");
            return null;
        }else{
            LOG.info("Resultat avec du contenu de la part du scheduler");
            LOG.info(resultPost.get());
            return bookingRepository.save(booking);
        }
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
