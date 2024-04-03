package fr.univcotedazur.isadevops.exceptions;

public class NotEnoughPlacesException extends Exception {
    public NotEnoughPlacesException() {
        super("Pas assez de places pour effectuer cette réservation.");
    }
}
