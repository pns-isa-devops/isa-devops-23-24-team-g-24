package fr.univcotedazur.isadevops.cli.commands;

import fr.univcotedazur.isadevops.cli.CliContext;
import fr.univcotedazur.isadevops.cli.model.CliActivity;
import fr.univcotedazur.isadevops.cli.model.CliBooking;
import fr.univcotedazur.isadevops.cli.model.CliCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@ShellComponent
public class BookingCommands {
    public static final String BASE_URI = "/booking";

    private final RestTemplate restTemplate;

    private final CliContext cliContext;
    @Autowired
    public BookingCommands(RestTemplate restTemplate, CliContext cliContext) {
        this.restTemplate = restTemplate;
        this.cliContext = cliContext;
    }
    @ShellMethod("Create a new booking (create-booking CUSTOMER_ID ACTIVITY_ID USE_POINTS)")
    public String createBooking(Long customerId, Long activityId, boolean usePoints) {
        CliBooking booking = new CliBooking(customerId, activityId, usePoints);
        System.out.println("Creating booking: " + booking.getActivityId() + " for customer " + booking.getCustomerId());
        ResponseEntity<CliBooking> response = restTemplate.postForEntity(BASE_URI, booking, CliBooking.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return "Booking created successfully: " + response.getBody();
        } else {
            return "Failed to create booking";
        }
    }

    @ShellMethod("List all bookings")
    public String listBookings() {
        ResponseEntity<CliBooking[]> response = restTemplate.getForEntity(BASE_URI, CliBooking[].class);
        CliBooking[] bookings = response.getBody();

        if (bookings == null || bookings.length == 0) {
            return "No bookings found.";
        } else {
            return Arrays.stream(bookings)
                    .map(CliBooking::toString)
                    .collect(Collectors.joining("\n"));
        }
    }

    @ShellMethod("Cancel a booking")
    public String cancelBooking(@ShellOption Long bookingId) {
        try {
            restTemplate.delete(BASE_URI + "/" + bookingId);
            return "Booking canceled successfully";
        } catch (HttpClientErrorException.NotFound e) {
            return "Booking not found";
        }
    }
}
