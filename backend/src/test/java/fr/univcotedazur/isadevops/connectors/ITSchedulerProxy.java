package fr.univcotedazur.isadevops.connectors;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Booking;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.*;
import fr.univcotedazur.isadevops.repositories.ActivityRepository;
import fr.univcotedazur.isadevops.repositories.BookingRepository;
import fr.univcotedazur.isadevops.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

import fr.univcotedazur.isadevops.components.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ITSchedulerProxy {

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

    @Autowired
    private BookingHandler bookingHandler;

    private static Customer testCustomer;
    private static Activity testActivity;

    @BeforeAll
    static public void setUp() {
        testCustomer = new Customer("John Doe", "8969837890");
        testCustomer.setId(1L);
        testActivity = new Activity("Hiking", "Mountain", 20L,5,5L);
        testActivity.setId(1L);
    }

    @Test
    public void createBooking_shouldCreateBooking_whenValidCustomerAndActivityGiven() throws ActivityIdNotFoundException, CustomerIdNotFoundException, PaymentException, NotEnoughPlacesException, NotEnoughPointsException {
        when(customerFinder.findById(testCustomer.getId())).thenReturn(Optional.of(testCustomer));
        when(activityFinder.findById(testActivity.getId())).thenReturn(Optional.of(testActivity));
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateString = currentDate.format(formatter);

        Booking booking = bookingHandler.createBooking(testCustomer.getId(), testActivity.getId(), false);

        assertNotNull(booking);
        assertEquals(testCustomer.getId(), booking.getCustomer().getId());
        assertEquals(testActivity.getId(), booking.getActivity().getId());
    }
}
