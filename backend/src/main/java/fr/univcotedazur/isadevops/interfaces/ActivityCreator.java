package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingActivityException;

public interface ActivityCreator {
    Activity create(String name, String localisation, long numberOfPlaces, double price, long pricePoints, long id_partner) throws AlreadyExistingActivityException;
}
