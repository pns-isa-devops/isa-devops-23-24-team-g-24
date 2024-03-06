package fr.univcotedazur.isadevops.exceptions;

public class AlreadyExistingBookingException extends Throwable {
    private String conflictingName;

    public AlreadyExistingBookingException(String name) {
        conflictingName = name;
    }
}
