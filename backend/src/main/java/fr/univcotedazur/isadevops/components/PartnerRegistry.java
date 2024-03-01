package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Partner;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingPartnerException;
import fr.univcotedazur.isadevops.interfaces.PartnerCreator;
import fr.univcotedazur.isadevops.repositories.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerRegistry implements PartnerCreator {
    private final PartnerRepository partnerRepository;

    @Autowired
    public PartnerRegistry(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Partner> findAllPartners() {
        return partnerRepository.findAll();
    }

    @Override
    @Transactional
    public Partner create(String name, String location, String description) throws AlreadyExistingPartnerException {
        if (findByName(name).isPresent())
            throw new AlreadyExistingPartnerException(name);
        Partner newPartner = new Partner(name, location, description);
        return partnerRepository.save(newPartner);
    }

    @Override
    @Transactional
    public void delete(long id) {
        partnerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Partner> findByName(String name) {
        return partnerRepository.findPartnerByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Partner> findById(long id) {
        return partnerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Partner> findAll() {
        return partnerRepository.findAll();
    }
}
