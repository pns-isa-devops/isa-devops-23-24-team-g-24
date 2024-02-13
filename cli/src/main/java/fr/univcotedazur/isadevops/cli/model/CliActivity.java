package fr.univcotedazur.isadevops.cli.model;

public class CliActivity {
    private Long id;
    private String name;
    private String location;
    private int pricePoints;
    private int numberOfPlaces;
    private int pointsEarned;

    public CliActivity(String name, String location, int pricePoints, int numberOfPlaces, int pointsEarned) {
        this.name = name;
        this.location = location;
        this.pricePoints = pricePoints;
        this.numberOfPlaces = numberOfPlaces;
        this.pointsEarned = pointsEarned;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPricePoints() {
        return pricePoints;
    }

    public void setPricePoints(int pricePoints) {
        this.pricePoints = pricePoints;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(int numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", pricePoints='" + pricePoints + '\'' +
                ", numberOfPlaces='" + numberOfPlaces + '\'' +
                ", pointsEarned='" + pointsEarned + '\'' +
                '}';
    }
}
