package fr.univcotedazur.isadevops.entities;

import fr.univcotedazur.isadevops.exceptions.NotEnoughPointsException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id; // Whether Long/Int or UUID are better primary keys, exposable outside is a vast issue, keep it simple here

    @NotBlank
    @Column(unique = true)
    private String name;

    @Pattern(regexp = "\\d{10}+", message = "Invalid creditCardNumber")
    private String creditCard;

    @NotNull
    private double pointsBalance = 0;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private UserGroup group;

//    @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY, mappedBy = "customer")
//    private Set<Order> orders = new HashSet<>();
//
//    @ElementCollection(fetch = FetchType.EAGER)
//    private Set<Item> cart = new HashSet<>();

    public Customer() {
    }

    public Customer(String n, String c) {
        this.name = n;
        this.creditCard = c;
        this.pointsBalance = 0;
    }
    public Customer(String n, String c, double p) {
        this.name = n;
        this.creditCard = c;
        this.pointsBalance = p;
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
    public double getPointsBalance() {
        return pointsBalance;
    }

    public void setPointsBalance(double pointsBalance) {
        this.pointsBalance = pointsBalance;
    }

    public UserGroup getGroup() {
        return group;
    }

    public void setGroup(UserGroup group) {
        this.group = group;
    }
    public void transferPointsTo(Customer recipient, double points) throws NotEnoughPointsException {
        if (this.pointsBalance < points) {
            throw new NotEnoughPointsException();
        }

        boolean sameGroup = Optional.ofNullable(this.group)
                .equals(Optional.ofNullable(recipient.getGroup()));

        double finalPoints = sameGroup ? points : points * 0.9;

        this.pointsBalance -= points;
        recipient.pointsBalance += finalPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return Objects.equals(name, customer.name) && Objects.equals(creditCard, customer.creditCard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, creditCard);
    }

    public void setId(long l) {
        this.id = l;
    }
}
