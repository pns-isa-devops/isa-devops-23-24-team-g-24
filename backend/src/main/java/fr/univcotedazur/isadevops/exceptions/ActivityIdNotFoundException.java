package fr.univcotedazur.isadevops.exceptions;

public class ActivityIdNotFoundException extends Exception {

    private Long id;

    public ActivityIdNotFoundException(Long id) {
        this.id = id;
    }

    public ActivityIdNotFoundException() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
