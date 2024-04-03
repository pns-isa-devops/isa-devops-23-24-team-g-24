package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Partner;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingPartnerException;
import fr.univcotedazur.isadevops.exceptions.PartnerNotFoundException;

import java.util.Optional;
import java.util.List;

public interface PartnerManager {
    Partner create(String name, String address, String description) throws AlreadyExistingPartnerException;
    void delete(long id) throws PartnerNotFoundException;
}
