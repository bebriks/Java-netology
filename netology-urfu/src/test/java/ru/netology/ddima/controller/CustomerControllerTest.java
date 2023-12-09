package ru.netology.ddima.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.ddima.OperationHistoryApiApplicationTest;
import ru.netology.ddima.controller.dto.CustomerDTO;
import ru.netology.ddima.controller.dto.GetClientResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest extends OperationHistoryApiApplicationTest{

    @Autowired
    private CustomerController customerController;

    @Test
    public void getClientsTest(){
        GetClientResponse customers = customerController.getClient();
        CustomerDTO customer1 = customers.getClients().get(0);
        CustomerDTO customer2 = customers.getClients().get(1);

        assertEquals(1, customer1.getId());
        assertEquals("Spring", customer1.getName());
        assertEquals(2, customer2.getId());
        assertEquals("Boot", customer2.getName());
    }

    @Nested
    class CustomerControllerTest1 extends OperationHistoryApiApplicationTest {


        @Autowired
        private MockMvc mockMvc;

        @Test
        void getClientShouldReturnListOfCustomerDTOs() throws Exception {
            mockMvc.perform(get("/api/customers"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        void getCustomerShouldReturnCustomerDTO() throws Exception {
            mockMvc.perform(get("/api/customers/{id}", 1))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").isString());
        }

        @Test
        void delCustomerShouldReturnOkStatus() throws Exception {
            mockMvc.perform(delete("/api/customers/{id}", 1))
                    .andExpect(status().isOk());
        }

        @Test
        void delCustomerShouldReturnNotFoundStatus() throws Exception {
            mockMvc.perform(delete("/api/customers/{id}", 999))
                    .andExpect(status().isNotFound());
        }

        @Test
        void addClientShouldReturnCreatedStatus() throws Exception {
            CustomerDTO customerDTO = new CustomerDTO(1, "John");
            String jsonContent = new ObjectMapper().writeValueAsString(customerDTO);

            mockMvc.perform(post("/api/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonContent))
                    .andExpect(status().isCreated());
        }
    }
}