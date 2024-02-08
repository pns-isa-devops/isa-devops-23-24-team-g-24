package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.CustomerIdNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CustomerFinder {

    Optional<Customer> findByName(String name);

    Optional<Customer> findById(Long id);

    Customer retrieveCustomer(Long customerId) throws CustomerIdNotFoundException;

    List<Customer> findAll();

}
