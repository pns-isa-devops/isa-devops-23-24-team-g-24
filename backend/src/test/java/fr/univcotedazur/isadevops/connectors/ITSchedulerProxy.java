package fr.univcotedazur.isadevops.connectors;

import fr.univcotedazur.isadevops.interfaces.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
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
