package fr.univcotedazur.isadevops.controllers;

import fr.univcotedazur.isadevops.dto.ErrorDTO;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.components.StatsRetriever;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

@RestController
@RequestMapping(path = StatsController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class StatsController {
    private final StatsRetriever statsRetriever;

    public static final String BASE_URI = "/stats";

    @Autowired
    public StatsController(StatsRetriever statsRetriever) {
        this.statsRetriever = statsRetriever;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    // The 422 (Unprocessable Entity) status code means the server understands the content type of the request entity
    // (hence a 415(Unsupported Media Type) status code is inappropriate), and the syntax of the request entity is
    // correct (thus a 400 (Bad Request) status code is inappropriate) but was unable to process the contained
    // instructions.
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        return new ErrorDTO("Cannot process Activity information", e.getMessage());
    }

    @GetMapping(path = "/activity/{activityId}")
    public ResponseEntity<String> getCustomers(@PathVariable("activityId") Long activityId) {
        return ResponseEntity.ok(statsRetriever.retrieveActivityStats(activityId));
    }

    @GetMapping(path = "/customer/{customerId}")
    public ResponseEntity<String> getStatsCustomer(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(statsRetriever.retrieveStatsCustomer(customerId));
    }

    @GetMapping(path = "/partner/{partnerId}")
    public ResponseEntity<String> getStatsPartner(@PathVariable("partnerId") Long partnerId) {
        return ResponseEntity.ok(statsRetriever.retrieveStatsPartner(partnerId));
    }
}
