package fr.univcotedazur.isadevops.cli.model;

public class CliActivity {
    private Long id;
    private String name;
    private String location;
    private double price;
    private int pricePoints;
    private long numberOfPlaces;
    private int pointsEarned;
    private long idPartner;

    public CliActivity(String name, String location, Long numberOfPlaces, Long idPartner) {
        this.name = name;
        this.location = location;
        this.numberOfPlaces = numberOfPlaces;
        this.idPartner = idPartner;
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


    public Long getNumberOfPlaces() {
        return numberOfPlaces;
    }

    public void setNumberOfPlaces(Long numberOfPlaces) {
        this.numberOfPlaces = numberOfPlaces;
    }

    public int getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(int pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getIdPartner() {
        return idPartner;
    }

    public void setIdPartner(long idPartner) {
        this.idPartner = idPartner;
    }


    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", numberOfPlaces='" + numberOfPlaces + '\'' +
                ", id='" + id + '\'' +
                ", idPartner='" + idPartner + '\'' +
                '}';
    }
}
