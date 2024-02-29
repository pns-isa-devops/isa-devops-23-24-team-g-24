package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Partner;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingPartnerException;

import java.util.Optional;
import java.util.List;

public interface PartnerCreator {
    Partner create(String name, String address, String description) throws AlreadyExistingPartnerException;
    Optional<Partner> findByName(String name);
    Optional<Partner> findById(long id);
    List<Partner> findAll();
}
