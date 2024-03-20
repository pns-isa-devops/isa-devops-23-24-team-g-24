package fr.univcotedazur.isadevops.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Activity activity;
    @NotNull
    private boolean usePoints;


    // Add default constructor for JPA
    public Booking() {}

    public Booking(Customer customer, Activity activity, boolean usePoints) {
        this.customer = customer;
        this.activity = activity;
        this.usePoints = usePoints;
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
