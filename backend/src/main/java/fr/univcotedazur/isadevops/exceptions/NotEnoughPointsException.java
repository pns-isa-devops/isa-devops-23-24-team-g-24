package fr.univcotedazur.isadevops.exceptions;

public class NotEnoughPointsException extends Exception {
    public NotEnoughPointsException() {
        super("Pas assez de points pour effectuer cette réservation.");
    }
}
