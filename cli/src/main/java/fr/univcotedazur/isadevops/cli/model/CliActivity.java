package fr.univcotedazur.isadevops.cli.model;

public class CliActivity {
    private Long id;
    private String name;
    private String location;
    private Long numberOfPlaces;
    private double price;
    private Long pointsEarned;
    private Long pricePoints;
    private Long idPartner;


    public CliActivity(String name, String location, Long numberOfPlaces,double price, Long pointsEarned, Long pricePoints, Long idPartner) {
        this.name = name;
        this.location = location;
        this.numberOfPlaces = numberOfPlaces;
        this.price = price;
        this.pointsEarned = pointsEarned;
        this.pricePoints = pricePoints;
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

    public Long getPricePoints() {
        return pricePoints;
    }
    public void setPricePoints(Long pricePoints) {
        this.pricePoints = pricePoints;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", numberOfPlaces='" + numberOfPlaces + '\'' +
                ", id='" + id + '\'' +
                ", price='" + price + '\'' +
                ", pricePoints='" + pricePoints + '\'' +
                ", idPartner='" + idPartner + '\'' +
                '}';
    }
}
