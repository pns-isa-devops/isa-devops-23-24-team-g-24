package fr.univcotedazur.isadevops.exceptions;

public class PartnerNotFoundException extends Throwable {
    private String conflictingName;
    public PartnerNotFoundException(String name) {
        conflictingName = name;
    }

}
