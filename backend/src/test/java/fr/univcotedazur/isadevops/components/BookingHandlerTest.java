package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Booking;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.ActivityIdNotFoundException;
import fr.univcotedazur.isadevops.exceptions.CustomerIdNotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
public class BookingHandlerTest {

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private ActivityRepository activityRepository;

    @Autowired
    private BookingHandler bookingHandler;

    private static Customer testCustomer;
    private static Activity testActivity;
    
    @MockBean
    private SchedulerProxy schedulerProxy;

    @BeforeAll
   static public void setUp() {
        testCustomer = new Customer("John Doe", "1234567890");
        testCustomer.setId(1L);
        testActivity = new Activity("Hiking", "Mountain", 20L);
        testActivity.setId(1L);
    }

    @Test
    public void createBooking_shouldCreateBooking_whenValidCustomerAndActivityGiven() throws ActivityIdNotFoundException, CustomerIdNotFoundException {
        when(customerRepository.findById(testCustomer.getId())).thenReturn(Optional.of(testCustomer));
        when(activityRepository.findById(testActivity.getId())).thenReturn(Optional.of(testActivity));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(schedulerProxy.book("2022", testActivity.getName(), "sophia")).thenReturn(Optional.of("test"));


        Booking booking = bookingHandler.createBooking(testCustomer.getId(), testActivity.getId());

        assertNotNull(booking);
        assertEquals(testCustomer.getId(), booking.getCustomer().getId());
        assertEquals(testActivity.getId(), booking.getActivity().getId());
    }

    @Test
    public void createBooking_shouldThrowException_whenInvalidCustomerGiven() throws ActivityIdNotFoundException, CustomerIdNotFoundException {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(schedulerProxy.book("2022", testActivity.getName(), "sophia")).thenReturn(Optional.of("test"));


        assertThrows(CustomerIdNotFoundException.class ,() -> bookingHandler.createBooking(999L, testActivity.getId()));
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
        List<Booking> bookings = List.of(new Booking(testCustomer, testActivity));
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> foundBookings = bookingHandler.findAllBookings();

        assertFalse(foundBookings.isEmpty());
        assertEquals(1, foundBookings.size());
        assertEquals(testCustomer.getName(), foundBookings.get(0).getCustomer().getName());
    }
}

