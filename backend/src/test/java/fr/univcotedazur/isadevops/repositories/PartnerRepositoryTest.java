package fr.univcotedazur.isadevops.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import fr.univcotedazur.isadevops.entities.Partner;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class PartnerRepositoryTest {

    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    @DirtiesContext
    void testFindPartnerByName() {
        // Arrange
        String partnerName = "TestPartner";
        Partner partner = new Partner();
        partner.setName(partnerName);
        partner.setLocation("TestLocation");  // Add location to satisfy the validation
        partnerRepository.save(partner);

        // Act
        Optional<Partner> foundPartner = partnerRepository.findPartnerByName(partnerName);

        // Assert
        assertTrue(foundPartner.isPresent());
        assertEquals(partnerName, foundPartner.get().getName());
    }

    @Test
    void testFindById() {
        // Arrange
        Partner partner = new Partner();
        partnerRepository.save(partner);

        // Act
        Optional<Partner> foundPartner = partnerRepository.findById(partner.getId());

        // Assert
        assertTrue(foundPartner.isPresent());
        assertEquals(partner.getId(), foundPartner.get().getId());
    }

    @Test
    void testFindByName_NotFound() {
        // Arrange
        String partnerName = "NonExistentPartner";

        // Act
        Optional<Partner> foundPartner = partnerRepository.findPartnerByName(partnerName);

        // Assert
        assertFalse(foundPartner.isPresent());
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        long nonExistentId = 12345L;

        // Act
        Optional<Partner> foundPartner = partnerRepository.findById(nonExistentId);

        // Assert
        assertFalse(foundPartner.isPresent());
    }
}
