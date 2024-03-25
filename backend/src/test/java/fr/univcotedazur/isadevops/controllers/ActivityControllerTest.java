package fr.univcotedazur.isadevops.controllers;
import fr.univcotedazur.isadevops.dto.ActivityDTO;
import fr.univcotedazur.isadevops.entities.Activity;
import fr.univcotedazur.isadevops.entities.Partner;
import fr.univcotedazur.isadevops.exceptions.AlreadyExistingActivityException;
import fr.univcotedazur.isadevops.services.ActivityService;
import io.cucumber.java.AfterAll;
import fr.univcotedazur.isadevops.repositories.ActivityRepository;
import fr.univcotedazur.isadevops.repositories.PartnerRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ActivityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @BeforeEach
    @Transactional
    void setUpContext() throws Exception {
        activityRepository.deleteAll();

        Partner partner = new Partner("partner", "partnerlocation", "partnerdescription");
        partnerRepository.save(partner);
    }

    @AfterAll
    @Transactional
    void tearDown() {
        partnerRepository.deleteAll();
    }

    @Test
    void validActivityTest() throws Exception {
        ActivityDTO validActivity = new ActivityDTO(null, "activity1", "description1", 10L, 5L, 10, 10L, 1L);
        mockMvc.perform(MockMvcRequestBuilders.post(ActivityController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validActivity)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void inValidActivityTestBlankName() throws Exception {
        // Creating an invalid activity with a blank name
        ActivityDTO invalidActivityWithBlankName = new ActivityDTO(null, "", "location", 10L, 5L, 10, 10L, 1L);
        mockMvc.perform(MockMvcRequestBuilders.post(ActivityController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidActivityWithBlankName)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void inValidActivityTestBlankDescription() throws Exception {
        // Creating an invalid activity with a blank description
        ActivityDTO invalidActivityWithBlankDescription = new ActivityDTO(null, "activity1", "", 10L, 5L, 10, 10L, 1L);
        mockMvc.perform(MockMvcRequestBuilders.post(ActivityController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidActivityWithBlankDescription)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }
}
