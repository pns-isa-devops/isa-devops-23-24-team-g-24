package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.connectors.BankProxy;
import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Booking;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.*;
import fr.univcotedazur.isadevops.repositories.ActivityRepository;
import fr.univcotedazur.isadevops.repositories.BookingRepository;
import fr.univcotedazur.isadevops.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.univcotedazur.isadevops.interfaces.*;
import fr.univcotedazur.isadevops.connectors.*;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
public class BookingHandlerTest {

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private CustomerFinder customerFinder;

    @MockBean
    private CustomerUpdater customerUpdater;
    @MockBean
    private CustomerRegistration customerRegistration;

    @MockBean
    private ActivityFinder activityFinder;
    @MockBean
    private ActivityCreator activityCreator;

    @MockBean
    private BankProxy bankProxy;

    @Autowired
    private BookingHandler bookingHandler;

    private static Customer testCustomer;
    private static Customer testCustomerWith0Points;
    private static Activity testActivity;

    @MockBean
    private SchedulerProxy schedulerProxy;

    @BeforeAll
   static public void setUp() {
        testCustomer = new Customer("John Doe", "8969837890");
        testCustomer.setPointsBalance(100L);
        testCustomer.setId(1L);
        testCustomerWith0Points = new Customer("John Doe", "8969837890");
        testCustomerWith0Points.setPointsBalance(0L);
        testCustomerWith0Points.setId(2L);
        testActivity = new Activity("Hiking", "Mountain", 20L,10, 10L);
        testActivity.setId(1L);
    }

    @Test
    public void createBooking_shouldCreateBooking_whenValidCustomerAndActivityGiven() throws ActivityIdNotFoundException, CustomerIdNotFoundException, PaymentException, NotEnoughPointsException, NotEnoughPlacesException {
        when(customerFinder.findById(testCustomer.getId())).thenReturn(Optional.of(testCustomer));
        when(activityFinder.findById(testActivity.getId())).thenReturn(Optional.of(testActivity));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(schedulerProxy.book(testActivity.getName(), "sophia")).thenReturn(Optional.of("test"));


        when(schedulerProxy.book(testActivity.getName(), "magicPartner")).thenReturn(Optional.of("test"));
        when(bankProxy.pay(any(Customer.class), anyDouble())).thenReturn(Optional.of("RECEIPT:628682be-f22f-4184-9c77-db47fc6c4952"));
        assertEquals(100, testCustomer.getPointsBalance());
        Booking booking = bookingHandler.createBooking(testCustomer.getId(), testActivity.getId(), false);
        assertEquals(120, testCustomer.getPointsBalance());
        assertNotNull(booking);
        assertEquals(testCustomer.getId(), booking.getCustomer().getId());
        assertEquals(testActivity.getId(), booking.getActivity().getId());
    }

    @Test
    public void createBooking_shouldThrowException_whenInvalidCustomerGiven() throws ActivityIdNotFoundException, CustomerIdNotFoundException {
        when(customerFinder.findById(anyLong())).thenReturn(Optional.empty());
        when(schedulerProxy.book(testActivity.getName(), "sophia")).thenReturn(Optional.of("test"));


        assertThrows(CustomerIdNotFoundException.class ,() -> bookingHandler.createBooking(999L, testActivity.getId(),false));
    }

    @Test
    public void createBooking_shouldThrowException_whenNotEnoughtPoints() throws ActivityIdNotFoundException, CustomerIdNotFoundException {
        when(customerFinder.findById(testCustomerWith0Points.getId())).thenReturn(Optional.of(testCustomerWith0Points));
        when(activityFinder.findById(testActivity.getId())).thenReturn(Optional.of(testActivity));
        assertThrows(NotEnoughPointsException.class ,() -> bookingHandler.createBooking(testCustomerWith0Points.getId(), testActivity.getId(),true));
    }

    @Test
    public void cancelBooking_shouldCancelBooking_whenValidBookingIdGiven() {
        Long bookingId = 1L;
        when(bookingRepository.existsById(bookingId)).thenReturn(true);
        doNothing().when(bookingRepository).deleteById(bookingId);
        boolean result = bookingHandler.cancelBooking(bookingId);
        assertTrue(result);
        verify(bookingRepository, times(1)).deleteById(bookingId);
    }

    @Test
    public void findAllBookings_shouldReturnAllBookings() {
        List<Booking> bookings = List.of(new Booking(testCustomer, testActivity,false));
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> foundBookings = bookingHandler.findAllBookings();

        assertFalse(foundBookings.isEmpty());
        assertEquals(1, foundBookings.size());
        assertEquals(testCustomer.getName(), foundBookings.get(0).getCustomer().getName());
    }
}

