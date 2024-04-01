package fr.univcotedazur.isadevops.components;

import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Customer;
import fr.univcotedazur.isadevops.entities.Partner;



import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class StatsRetrieverTest {
    
        @Autowired
        private StatsRetriever statsRetriever;

        private static final String nameCustomer = "John";
        private static final String creditCard = "1234567890";

        private static final String namePartner = "NicePartner";
        private static final String location = "Nice";
        private static final String description = "Nice place";

        private static final String activityName = "Swimming";
        private static final String activityDescription = "Swimming pool";
        private static final long activitySize = 10L;

        private static Customer testCustomer;
        private static Activity testActivity;
        private static Partner testPartner;

        @BeforeAll
        static public void setUp() {
            testCustomer = new Customer(nameCustomer, creditCard);
            testCustomer.setId(1L);

            testActivity = new Activity(activityName, activityDescription, activitySize, 10,5L, 10L);
            testActivity.setId(1L);

            testPartner = new Partner(namePartner, location, description);
            testPartner.setId(1L);
        }
        
        @Test
        void retrieveStatsCustomerWithFalseID() {
            String stats = statsRetriever.retrieveStatsCustomer(88L);
            assertEquals(stats, "Customer not found");
        }
    
        @Test
        void retrieveStatsPartnerWithFalseID() {
            String stats = statsRetriever.retrieveStatsPartner(88L);
            assertEquals(stats, "Partner not found");
        }
    
}
