package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.PaymentException;

public interface Payment {
    public void pay(double price, Customer customer) throws PaymentException;
}
