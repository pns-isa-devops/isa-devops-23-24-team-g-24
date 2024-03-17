package fr.univcotedazur.isadevops.connectors;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class ITSchedulerProxy {
    @Autowired
    private SchedulerProxy schedulerProxy;

    @Test
    void bookAnActivity() {
        assertFalse(schedulerProxy.book("2021-12-31", "activity", "partner").isEmpty());
    }

    @Test
    void bookAnActivityAlreadeBooked() {
        assertFalse(schedulerProxy.book("2023-03-17", "activity", "partner").isEmpty());
        assertTrue(schedulerProxy.book("2023-03-17", "activity", "partner").isEmpty());
    }
}
