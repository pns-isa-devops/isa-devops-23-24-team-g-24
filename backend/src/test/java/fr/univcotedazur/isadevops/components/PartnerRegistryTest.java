package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Partner;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingPartnerException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PartnerRegistryTest {

    @Autowired
    private PartnerRegistry partnerRegistry;

    private final String name = "John";
    private final String location = "Nice";
    private final String description = "Nice place";

    @Test
    void unknownPartner() {
        assertFalse(partnerRegistry.findByName(name).isPresent());
    }

    @Test
    void registerPartner() throws AlreadyExistingPartnerException {
        Partner returned = partnerRegistry.create(name, location, description);
        Optional<Partner> partner = partnerRegistry.findByName(name);
        assertTrue(partner.isPresent());
        Partner john = partner.get();
        assertEquals(john, returned);
        assertEquals(john, partnerRegistry.findById(returned.getId()).get());
        assertEquals(name, john.getName());
        assertEquals(location, john.getLocation());
        assertEquals(description, john.getDescription());
    }

    @Test
    void cannotRegisterTwice() throws AlreadyExistingPartnerException {
        partnerRegistry.create(name, location, description);
        Assertions.assertThrows(AlreadyExistingPartnerException.class, () -> partnerRegistry.create(name, location, description));
    }

    @Transactional
    @Test
    void findAllPartnersEmptyInitially() {
        Optional<Partner> toDelete=partnerRegistry.findByName("DuplicatePartner");
        if(toDelete.isPresent()){
            partnerRegistry.delete(toDelete.get().getId());
        }
        List<Partner> partners = partnerRegistry.findAllPartners();
        assertTrue(partners.isEmpty());
    }

    @Transactional
    @Test
    void findAllPartners() throws AlreadyExistingPartnerException {
        Optional<Partner> toDelete=partnerRegistry.findByName("DuplicatePartner");
        if(toDelete.isPresent()){
            partnerRegistry.delete(toDelete.get().getId());
        }
        partnerRegistry.create(name, location, description);
        List<Partner> partners = partnerRegistry.findAllPartners();
        assertFalse(partners.isEmpty());
        assertEquals(1, partners.size());
    }

    @Test
    void deletePartner() throws AlreadyExistingPartnerException {
        Partner registered = partnerRegistry.create(name, location, description);
        partnerRegistry.delete(registered.getId());
        assertFalse(partnerRegistry.findByName(name).isPresent());
    }


    @Test
    void findByName() throws AlreadyExistingPartnerException {
        Partner registered = partnerRegistry.create(name, location, description);
        Optional<Partner> partner = partnerRegistry.findByName(name);
        assertTrue(partner.isPresent());
        assertEquals(registered, partner.get());
    }


}
