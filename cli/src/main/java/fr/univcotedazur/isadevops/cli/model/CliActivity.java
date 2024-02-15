package fr.univcotedazur.isadevops.cli.model;

public class CliActivity {
    private Long id;
    private String name;
    private String location;
    private Long numberOfPlaces;

    public CliActivity(String name, String location, Long numberOfPlaces) {
        this.name = name;
        this.location = location;
        this.numberOfPlaces = numberOfPlaces;
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

    @Override
    public String toString() {
        return "Activity{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", numberOfPlaces='" + numberOfPlaces + '\'' +
                '}';
    }
}
