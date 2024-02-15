package fr.univcotedazur.isadevops.entities;

import org.springframework.data.annotation.Id;

public class Booking {

    @Id
    private long id;
    private Customer customer;
    private Activity activity;

    public Booking(Customer customer, Activity activity) {
        this.customer = customer;
        this.activity = activity;
    }
}
