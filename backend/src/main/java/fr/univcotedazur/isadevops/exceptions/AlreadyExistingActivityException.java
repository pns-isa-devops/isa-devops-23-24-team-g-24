package fr.univcotedazur.isadevops.exceptions;

public class AlreadyExistingActivityException extends Throwable {
    private String conflictingName;

    public AlreadyExistingActivityException(String name) {
        conflictingName = name;
    }
}
