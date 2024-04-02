package fr.univcotedazur.isadevops.controllers;

import fr.univcotedazur.isadevops.dto.CustomerDTO;
import fr.univcotedazur.isadevops.dto.ErrorDTO;
import fr.univcotedazur.isadevops.dto.TransferPointsRequestDTO;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingCustomerException;
import fr.univcotedazur.isadevops.exceptions.CustomerIdNotFoundException;
import fr.univcotedazur.isadevops.exceptions.NotEnoughPointsException;
import fr.univcotedazur.isadevops.interfaces.CustomerFinder;
import fr.univcotedazur.isadevops.interfaces.CustomerRegistration;
import fr.univcotedazur.isadevops.interfaces.CustomerUpdater;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = CustomerCareController.BASE_URI, produces = APPLICATION_JSON_VALUE)
public class CustomerCareController {

    public static final String BASE_URI = "/customers";

    private final CustomerRegistration registry;

    private final CustomerFinder finder;
    private final CustomerUpdater updater;

    @Autowired
    public CustomerCareController(CustomerRegistration registry, CustomerFinder finder, CustomerUpdater updater) {
        this.registry = registry;
        this.finder = finder;
        this.updater = updater;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorDTO handleExceptions(MethodArgumentNotValidException e) {
        return new ErrorDTO("Cannot process Customer information", e.getMessage());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> register(@RequestBody @Valid CustomerDTO cusdto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(convertCustomerToDto(registry.register(cusdto.name(), cusdto.creditCard())));
        } catch (AlreadyExistingCustomerException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        return ResponseEntity.ok(finder.findAll().stream().map(CustomerCareController::convertCustomerToDto).toList());
    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("customerId") Long customerId) throws CustomerIdNotFoundException {
        return ResponseEntity.ok(convertCustomerToDto(finder.retrieveCustomer(customerId)));
    }

    private static CustomerDTO convertCustomerToDto(Customer customer) { // In more complex cases, we could use a ModelMapper such as MapStruct
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getCreditCard());
    }
    @PostMapping("/transferPoints")
    public ResponseEntity<String> transferPoints(@RequestBody TransferPointsRequestDTO request) {
        try {
            updater.transferPoints(request.getFromCustomerId(), request.getToCustomerId(), request.getPoints());
            return ResponseEntity.ok("Transfert de points effectué avec succès.");
        } catch (CustomerIdNotFoundException | NotEnoughPointsException e) {
            return ResponseEntity.badRequest().body("Erreur lors du transfert de points : " + e.getMessage());
        }
    }
}

