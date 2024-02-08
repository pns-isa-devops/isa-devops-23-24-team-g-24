package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingCustomerException;
import fr.univcotedazur.isadevops.interfaces.CustomerFinder;
import fr.univcotedazur.isadevops.interfaces.CustomerRegistration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional // default behavior : rollback DB operations after each test (even if it fails)
class CustomerRegistryTest {

    @Autowired
    private CustomerRegistration customerRegistration;

    @Autowired
    private CustomerFinder customerFinder;

    private final String name = "John";
    private final String creditCard = "1234567890";

    @Test
    void unknownCustomer() {
        assertFalse(customerFinder.findByName(name).isPresent());
    }

    @Test
    void registerCustomer() throws Exception {
        Customer returned = customerRegistration.register(name, creditCard);
        Optional<Customer> customer = customerFinder.findByName(name);
        assertTrue(customer.isPresent());
        Customer john = customer.get();
        assertEquals(john, returned);
        assertEquals(john, customerFinder.findById(returned.getId()).get());
        assertEquals(name, john.getName());
        assertEquals(creditCard, john.getCreditCard());
    }

    @Test
    void cannotRegisterTwice() throws Exception {
        customerRegistration.register(name, creditCard);
        Assertions.assertThrows(AlreadyExistingCustomerException.class, () -> customerRegistration.register(name, creditCard));
    }

}
