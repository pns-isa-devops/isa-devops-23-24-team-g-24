package fr.univcotedazur.isadevops.exceptions;

public class AlreadyExistingPartnerException extends Throwable{
    private String conflictingName;
    public AlreadyExistingPartnerException(String name) {
        conflictingName = name;
    }

}
