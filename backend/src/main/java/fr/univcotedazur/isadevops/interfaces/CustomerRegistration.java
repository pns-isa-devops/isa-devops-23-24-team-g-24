package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingCustomerException;

public interface CustomerRegistration {

    Customer register(String name, String creditCard)
            throws AlreadyExistingCustomerException;
}
