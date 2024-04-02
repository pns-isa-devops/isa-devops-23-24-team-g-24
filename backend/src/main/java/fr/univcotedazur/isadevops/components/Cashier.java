package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.PaymentException;
import fr.univcotedazur.isadevops.interfaces.Bank;
import fr.univcotedazur.isadevops.interfaces.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Cashier implements Payment {
    private Bank bank;
    private static final Logger LOG = LoggerFactory.getLogger(Cashier.class);
    @Autowired
    public Cashier(Bank bank) {
        this.bank = bank;
    }


    public void pay(double price, Customer customer) throws PaymentException {
        if (customer.getCreditCard() == null || customer.getCreditCard().isEmpty()) {
            throw new PaymentException(customer.getName(), price);
        }
        System.out.println("Paying " + price + " with credit card " + customer.getCreditCard());
        Optional<String> response = bank.pay(customer, price);
        LOG.info("Response from bank: {}", response);
        if (response.isEmpty()) {
            throw new PaymentException(customer.getName(), price);
        }
    }
}
