package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.entities.Partner;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingActivityException;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingCustomerException;
import fr.univcotedazur.isadevops.interfaces.ActivityCreator;
import fr.univcotedazur.isadevops.repositories.ActivityRepository;
import fr.univcotedazur.isadevops.repositories.PartnerRepository;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
public class ActivityServiceTest {
    @Autowired
    private ActivityCreator activityCreator;

    private final String name = "Tennis";
    private final String localisation = "Nice";
    private final long numberOfPlaces = 10;
    private final double price = 10;
    private final int pricePoints = 10;


    @Autowired
    private PartnerRepository partnerRepository;
        

    @Test
    void unknownCustomer() {
        assertFalse(activityCreator.findByName(name).isPresent());
    }

    @Test
    void addActivity() throws Exception, AlreadyExistingActivityException {
        Activity activity = activityCreator.create(name, localisation, numberOfPlaces,price, pricePoints, 0L);
        Optional<Activity> returned = activityCreator.findByName(name);
        assertTrue(returned.isPresent());
        Activity activityReturned = returned.get();
        assertEquals(activity, activityReturned);
        assertEquals(activity, activityCreator.findById(activityReturned.getId()).get());
        assertEquals(name, activityReturned.getName());
    }

    @Test
    void cannotAddTwice() throws Exception, AlreadyExistingActivityException {
        activityCreator.create(name, localisation, numberOfPlaces,price, pricePoints, 0L);
        Assertions.assertThrows(AlreadyExistingActivityException.class, () -> activityCreator.create(name, localisation, numberOfPlaces,price, pricePoints, 0L));
    }

    @Test
    void findAllCustomersEmptyInitially() {
        List<Activity> activities = activityCreator.findAllActivities();
        assertTrue(activities.isEmpty());
    }

    @Test
    void findAllCustomers() throws Exception, AlreadyExistingActivityException {
        activityCreator.create(name, localisation, numberOfPlaces,price, pricePoints, 0L);
        List<Activity> activities = activityCreator.findAllActivities();
        assertFalse(activities.isEmpty());
        assertEquals(1, activities.size());
        activityCreator.create("Football", "Nice", 20,20, 20, 0L);
        activities = activityCreator.findAllActivities();
        assertFalse(activities.isEmpty());
        assertEquals(2, activities.size());
    }
}
