package fr.univcotedazur.isadevops.repositories;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.repositories.ActivityRepository;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
@DataJpaTest
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void testIdGenerationAndUnicity() {
        Activity padel = new Activity("Padel", "Sophia Antipolis", 55L, 5L, 10, 10L);
        Assertions.assertNull(padel.getId());
        activityRepository.saveAndFlush(padel);
        Assertions.assertNotNull(padel.getId());
        //Assertions.assertThrows(DataIntegrityViolationException.class, () -> activityRepository.saveAndFlush(new Activity("Padel", "Sophia Antipolis", 5L)));
        //Assertions.assertThrows(DataIntegrityViolationException.class, () -> activityRepository.saveAndFlush(new Activity("Padel", "Sophia Antipolis", 3L)));
        //Rajouter @Column(unique = true) dans la classe Activity devant les attributs que l'on veut avoir comme uniques
    }

    @Test
    void testFindActivityByName() {
        Activity padel = new Activity("Padel", "Sophia Antipolis", 55L, 5L, 10, 10L);
        activityRepository.saveAndFlush(padel);
        Assertions.assertEquals(activityRepository.findActivityByName("Padel").get(),padel);
    }

    @Test
    void testBlankName() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> activityRepository.saveAndFlush(new Activity("", "Sophia Antipolis", 55L, 5L, 10, 10L)));
        Assertions.assertThrows(ConstraintViolationException.class, () -> activityRepository.saveAndFlush(new Activity("    ", "Sophia Antipolis", 55L, 5L, 10, 10L)));
    }

    @Test
    void testBlankLocation() {
        Assertions.assertThrows(ConstraintViolationException.class, () -> activityRepository.saveAndFlush(new Activity("padel", "", 1L, 5L, 10, 10L)));
        Assertions.assertThrows(ConstraintViolationException.class, () -> activityRepository.saveAndFlush(new Activity("padel", " ", 1L, 5L, 10, 10L)));
        Assertions.assertThrows(ConstraintViolationException.class, () -> activityRepository.saveAndFlush(new Activity("padel", "     ", 1L, 5L, 10, 10L)));
    }


}
