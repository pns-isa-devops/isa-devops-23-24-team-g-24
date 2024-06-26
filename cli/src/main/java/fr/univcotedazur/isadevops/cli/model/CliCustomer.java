package fr.univcotedazur.isadevops.cli.model;

// A cli side class being equivalent to the backend CustomerDTO, in terms of attributes
// so that the automatic JSON (de-)/serialization will make the two compatible on each side
public class CliCustomer {

    private Long id;
    private String name;
    private String creditCard;
    private double pointsBalance;
    private Long groupId;

    public CliCustomer(String name, String creditCard, double pointsBalance) {
        this.name = name;
        this.creditCard = creditCard;
        this.pointsBalance = pointsBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", creditCard='" + creditCard + '\'' +
                ", pointsBalance='" + pointsBalance + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
