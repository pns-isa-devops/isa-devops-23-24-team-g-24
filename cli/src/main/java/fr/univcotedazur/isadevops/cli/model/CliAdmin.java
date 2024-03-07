package fr.univcotedazur.isadevops.cli.model;

public class CliAdmin {
    private Long id;
    private String name;
    private String location;
    private String description;

    public CliAdmin(String name,String location, String description){
        this.name = name;
        this.location = location;
        this.description = description;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CliAdmin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
