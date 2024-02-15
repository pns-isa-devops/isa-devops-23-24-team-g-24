package fr.univcotedazur.isadevops.entities;


import jakarta.persistence.*;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Activity activity;

    // Add default constructor for JPA
    public Booking() {}

    public Booking(Customer customer, Activity activity) {
        this.customer = customer;
        this.activity = activity;
    }

    // Getters and setters
}
