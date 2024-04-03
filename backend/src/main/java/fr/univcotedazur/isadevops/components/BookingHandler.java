package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Booking;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.*;
import fr.univcotedazur.isadevops.interfaces.*;
import fr.univcotedazur.isadevops.repositories.BookingRepository;
import fr.univcotedazur.isadevops.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookingHandler implements BookingCreator, BookingFinder {
    private final BookingRepository bookingRepository;
    private final CustomerUpdater customerUpdater;
    private final ActivityFinder activityFinder;
    private final CustomerFinder customerFinder;
    private final Scheduler scheduler;
    private final Payment payment;
    private static final Logger LOG = LoggerFactory.getLogger(BookingHandler.class);

    @Autowired
    public BookingHandler(
                          BookingRepository bookingRepository,
                          ActivityFinder activityFinder,
                          CustomerFinder customerFinder,
                          CustomerUpdater customerUpdater,
                          Scheduler scheduler,
                          Payment payment
                          ){

        this.bookingRepository = bookingRepository;
        this.customerUpdater = customerUpdater;
        this.activityFinder = activityFinder;
        this.customerFinder = customerFinder;

        this.scheduler = scheduler;
        this.payment = payment;
    }

    @Override
    @Transactional
    public Booking createBooking(Long customerId, Long activityId, boolean usePoints) throws CustomerIdNotFoundException, ActivityIdNotFoundException, PaymentException, NotEnoughPointsException, NotEnoughPlacesException {
        Customer customer = customerFinder.findById(customerId)
                .orElseThrow(() -> new CustomerIdNotFoundException(customerId));
        Activity activity = activityFinder.findById(activityId)
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
            if(activity.getPrice() == -1){
                throw new PaymentException();
            }
            payment.pay(activity.getPrice(), customer);
            System.out.println("PAYINGGGGG");
            //Utilisation d'une regex pour détecter si l'activité booké est un forfait de ski ou non
            if(!activity.getName().toLowerCase().matches(".*forfait.*ski.*")){
                System.out.println("GETTING POINTS");
                System.out.println("Activity price : " + activity.getPrice());
                System.out.println("Customer points balance : " + customer.getPointsBalance());
                customer.setPointsBalance(customer.getPointsBalance() + activity.getPrice()*2);
                System.out.println("Customer points balance after : " + customer.getPointsBalance());
            }
        }
        customerUpdater.updateCustomer(customer);

        Booking booking = new Booking(customer, activity, usePoints);

        Optional<String> resultPost = this.scheduler.book(activity.getName(), "magicPartner");
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
