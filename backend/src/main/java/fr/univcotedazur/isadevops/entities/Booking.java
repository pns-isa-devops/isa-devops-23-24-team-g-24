package fr.univcotedazur.isadevops.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Activity activity;
    @NotBlank
    private boolean usePoints;


    // Add default constructor for JPA
    public Booking() {}

    public Booking(Customer customer, Activity activity, boolean usePoints) {
        this.customer = customer;
        this.activity = activity;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Activity getActivity() {
        return activity;
    }
    public boolean getUsePoints() {
        return usePoints;
    }
}
