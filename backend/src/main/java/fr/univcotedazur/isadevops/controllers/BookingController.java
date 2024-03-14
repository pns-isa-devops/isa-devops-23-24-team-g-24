package fr.univcotedazur.isadevops.controllers;

import fr.univcotedazur.isadevops.components.BookingHandler;
import fr.univcotedazur.isadevops.dto.BookingDTO;
import fr.univcotedazur.isadevops.entities.Booking;
import fr.univcotedazur.isadevops.exceptions.ActivityIdNotFoundException;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingActivityException;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingBookingException;
import fr.univcotedazur.isadevops.exceptions.CustomerIdNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingHandler bookingHandler;

    @Autowired
    public BookingController(BookingHandler bookingHandler) {
        this.bookingHandler = bookingHandler;
    }

    @PostMapping
    public ResponseEntity<Object> createBooking(@RequestBody @Valid BookingDTO bookingDTO) {
        System.out.println("Creation of a booking for customer " + bookingDTO.customerId() + " and activity " + bookingDTO.activityId());
        try {
            Booking booking = bookingHandler.createBooking(bookingDTO.customerId(), bookingDTO.activityId(), bookingDTO.usePoints());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertToDTO(booking));
        } catch (CustomerIdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer ID " + bookingDTO.customerId() + " not found.");
        } catch (ActivityIdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Activity ID " + bookingDTO.activityId() + " not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the booking.");
        }
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        boolean result = bookingHandler.cancelBooking(bookingId);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<BookingDTO>> listAllBookings() {
        List<Booking> bookings = bookingHandler.findAllBookings();
        return ResponseEntity.ok(bookings.stream().map(this::convertToDTO).collect(Collectors.toList()));
    }

    // Additional endpoints to list bookings by customer or activity can be similar to listAllBookings()

    private BookingDTO convertToDTO(Booking booking) {
        return new BookingDTO(booking.getId(), booking.getCustomer().getId(), booking.getActivity().getId(), booking.getUsePoints());
    }
}
