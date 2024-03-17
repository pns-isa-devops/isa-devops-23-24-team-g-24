package fr.univcotedazur.isadevops.controllers;

import fr.univcotedazur.isadevops.dto.CustomerDTO;
import fr.univcotedazur.isadevops.components.CustomerRegistry;
import fr.univcotedazur.isadevops.repositories.CustomerRepository;

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
public class CustomerCareControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @Transactional
    void setUpContext() throws Exception {
        customerRepository.deleteAll();
    }

    @Test
    void validCustomerTest() throws Exception {
        CustomerDTO validCustomer = new CustomerDTO(null, "john", "1234567890");
        mockMvc.perform(MockMvcRequestBuilders.post(CustomerCareController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validCustomer)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void inValidCustomerTest() throws Exception {
        CustomerDTO badCreditCardCustomer = new CustomerDTO(null, "badCreditCard", "123");
        mockMvc.perform(MockMvcRequestBuilders.post(CustomerCareController.BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badCreditCardCustomer)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

}
