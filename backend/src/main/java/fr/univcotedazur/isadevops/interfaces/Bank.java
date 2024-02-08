package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Customer;

import java.util.Optional;

public interface Bank {

    Optional<String> pay(Customer customer, double value);
}
