package fr.univcotedazur.isadevops.services;

import fr.univcotedazur.isadevops.entities.Partner;
import fr.univcotedazur.isadevops.repositories.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    private final PartnerRepository partnerRepository;



    @Override
    @Transactional(readOnly = true)
    public Optional<Partner> findByName(String name) {
        return partnerRepository.findPartnerByName(name);
    }

    @Override
    @Transactional
    public Partner create(String name, String location, String description)throws AlreadyExistingPartnerException{
        if(findByName(name).isPresent())
            throw new AlreadyExistingPartnerException(name);
        Partner newPartner = new Partner(name, location, description);
        return adminRepository.save(newPartner);
    }


}
