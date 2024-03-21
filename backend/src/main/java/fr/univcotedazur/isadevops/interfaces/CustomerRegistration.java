package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingCustomerException;
import fr.univcotedazur.isadevops.exceptions.PaymentException;

public interface CustomerRegistration {

    Customer register(String name, String creditCard)
            throws AlreadyExistingCustomerException;
}
