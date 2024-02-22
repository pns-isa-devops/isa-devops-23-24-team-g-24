package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Partner;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingPartnerException;

import java.util.Optional;

public interface PartnerCreator {
    Partner create(String name, String address, String phoneNumber) throws AlreadyExistingPartnerException;
    Optional<Partner> findByName(String name);
    //Optional<Partner> findById(long id);
}
