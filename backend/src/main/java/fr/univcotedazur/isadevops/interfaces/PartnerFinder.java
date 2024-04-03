package fr.univcotedazur.isadevops.interfaces;

import fr.univcotedazur.isadevops.entities.Partner;

import java.util.List;
import java.util.Optional;

public interface PartnerFinder {
    Optional<Partner> findByName(String name);
    Optional<Partner> findById(long id);
    List<Partner> findAll();
    List<Partner> findAllPartners();
}
