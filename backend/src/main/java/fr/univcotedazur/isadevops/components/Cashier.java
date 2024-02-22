package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.connectors.BankProxy;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.exceptions.PaymentException;
import fr.univcotedazur.isadevops.interfaces.Bank;
import fr.univcotedazur.isadevops.interfaces.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
public class Cashier implements Payment {
    private static Cashier instance;
    private Bank bank;

    @Autowired
    public Cashier(Bank bank) {
        this.bank = bank;
    }






    public void pay(double price, Customer customer) throws PaymentException {
        if (customer.getCreditCard() == null || customer.getCreditCard().isEmpty()) {
            throw new PaymentException(customer.getName(), price);
        }

        Optional<String> response = bank.pay(customer, price);
        if (response.isEmpty()) {
            throw new PaymentException(customer.getName(), price);
        }
    }
}
