package fr.univcotedazur.isadevops.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.univcotedazur.isadevops.dto.PartnerDTO;
import fr.univcotedazur.isadevops.repositories.PartnerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PartnerRepository partnerRepository;
    @BeforeEach
    @Transactional
    void setUpContext() throws Exception {
        partnerRepository.deleteAll();
    }

    @Test
    void validPartnerTest() throws Exception{
        PartnerDTO validPartner= new PartnerDTO(null, "partner1", "location1", "description1");
        mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPartner)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetPartnerById() throws Exception {
        // Créez un partenaire pour lequel vous connaissez l'ID
        PartnerDTO partnerToAdd = new PartnerDTO(null, "PartnerToGet", "LocationToGet", "DescriptionToGet");
        mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(partnerToAdd)));

        // Récupérez l'ID du partenaire créé
        Long partnerId = partnerRepository.findPartnerByName("PartnerToGet").get().getId();

        // Testez la récupération du partenaire par ID
        mockMvc.perform(MockMvcRequestBuilders.get(AdminController.BASE_URI + "/{id}", partnerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("PartnerToGet"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("LocationToGet"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("DescriptionToGet"));
    }

    @Test
    void testDeletePartner() throws Exception {
        // Créez un partenaire à supprimer
        PartnerDTO partnerToDelete = new PartnerDTO(null, "PartnerToDelete", "LocationToDelete", "DescriptionToDelete");
        mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partnerToDelete)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Récupérez l'ID du partenaire créé
        Long partnerId = partnerRepository.findPartnerByName("PartnerToDelete").get().getId();

        // Testez la suppression du partenaire par ID
        mockMvc.perform(MockMvcRequestBuilders.delete(AdminController.BASE_URI + "/{id}", partnerId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Assurez-vous que le partenaire a été supprimé en tentant de le récupérer à nouveau
        mockMvc.perform(MockMvcRequestBuilders.get(AdminController.BASE_URI + "/{id}", partnerId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testAddExistingPartner() throws Exception {
        PartnerDTO partnerToAdd = new PartnerDTO(null, "DuplicatePartner", "Location", "Description");

        // Ajoutez le partenaire une première fois
        mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partnerToAdd)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Essayez d'ajouter le même partenaire une deuxième fois
        mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partnerToAdd)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void testGetAllPartners() throws Exception {
        // Ajoutez quelques partenaires
        PartnerDTO partner1 = new PartnerDTO(null, "Partner1", "Location1", "Description1");
        PartnerDTO partner2 = new PartnerDTO(null, "Partner2", "Location2", "Description2");

        mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partner1)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partner2)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Testez la récupération de tous les partenaires
        mockMvc.perform(MockMvcRequestBuilders.get(AdminController.BASE_URI))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    void testAddPartnerValidation() throws Exception {
        PartnerDTO invalidPartner = new PartnerDTO(null, "", "Location", "Description");

        // Essayez d'ajouter un partenaire avec des données invalides
        mockMvc.perform(MockMvcRequestBuilders.post(AdminController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPartner)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

}
