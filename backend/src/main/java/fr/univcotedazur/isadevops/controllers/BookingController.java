package fr.univcotedazur.isadevops.controllers;

import fr.univcotedazur.isadevops.components.BookingHandler;
import fr.univcotedazur.isadevops.dto.BookingDTO;
import fr.univcotedazur.isadevops.entities.Booking;
import fr.univcotedazur.isadevops.exceptions.ActivityIdNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import fr.univcotedazur.isadevops.exceptions.CustomerIdNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private static final Logger LOG = LoggerFactory.getLogger(BookingController.class);
    private final BookingHandler bookingHandler;

    @Autowired
    public BookingController(BookingHandler bookingHandler) {
        this.bookingHandler = bookingHandler;
    }

    @PostMapping
    public ResponseEntity<Object> createBooking(@RequestBody @Valid BookingDTO bookingDTO) {
        LOG.info("Creation of a booking for customer {} and activity {}", bookingDTO.customerId(), bookingDTO.activityId());
        try {
            Booking booking = bookingHandler.createBooking(bookingDTO.customerId(), bookingDTO.activityId(), bookingDTO.usePoints());
            if(booking == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Activity already booked for this time slot");
            }else {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(convertToDTO(booking));
            }
        } catch (CustomerIdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Customer ID " + bookingDTO.customerId() + " not found.");
        } catch (ActivityIdNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Activity ID " + bookingDTO.activityId() + " not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the booking: "+e.getMessage());
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
        return ResponseEntity.ok(bookings.stream().map(this::convertToDTO).toList());
    }

    private BookingDTO convertToDTO(Booking booking) {
        return new BookingDTO(booking.getId(), booking.getCustomer().getId(), booking.getActivity().getId(), booking.getUsePoints());
    }
}
