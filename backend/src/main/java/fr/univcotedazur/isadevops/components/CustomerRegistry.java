package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingCustomerException;
import fr.univcotedazur.isadevops.exceptions.CustomerIdNotFoundException;
import fr.univcotedazur.isadevops.exceptions.PaymentException;
import fr.univcotedazur.isadevops.interfaces.Bank;
import fr.univcotedazur.isadevops.interfaces.CustomerFinder;
import fr.univcotedazur.isadevops.interfaces.CustomerRegistration;
import fr.univcotedazur.isadevops.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerRegistry implements CustomerRegistration, CustomerFinder {

    private final Bank bank;

    private final CustomerRepository customerRepository;

    @Autowired // annotation is optional since Spring 4.3 if component has only one constructor
    public CustomerRegistry(Bank bank, CustomerRepository customerRepository) {
        this.bank = bank;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer register(String name, String creditCard)
            throws AlreadyExistingCustomerException, PaymentException {
        if (findByName(name).isPresent())
            throw new AlreadyExistingCustomerException(name);
        Customer newcustomer = new Customer(name, creditCard);
        return customerRepository.save(newcustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findByName(String name) {
        return customerRepository.findCustomerByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer retrieveCustomer(Long customerId) throws CustomerIdNotFoundException {
        return findById(customerId).orElseThrow(() -> new CustomerIdNotFoundException(customerId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

}
