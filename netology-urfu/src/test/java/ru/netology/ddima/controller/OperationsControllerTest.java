package ru.netology.ddima.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.ddima.OperationHistoryApiApplicationTest;
import ru.netology.ddima.controller.dto.GetOperationResponse;
import ru.netology.ddima.controller.dto.OperationDTO;
import ru.netology.ddima.domain.Operation;
import ru.netology.ddima.service.StatementService;

import java.util.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class OperationsControllerTest {

    @Mock
    private StatementService statementService;

    @Autowired
    private TestRestTemplate restTemplate;
    @InjectMocks
    private OperationsController operationsController;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOperationsByUser() {
        // Arrange
        int customerId = 1;
        List<Operation> operations = new ArrayList<>();
        operations.add(new Operation(1, 5000, "Rub", "Nike"));

        when(statementService.getOperationOnId(customerId)).thenReturn(operations);

        // Act
        GetOperationResponse response = operationsController.getOperationsByUser(customerId);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getOperations().size());
        assertEquals(5000, response.getOperations().get(0).getSum());
        assertEquals("Rub", response.getOperations().get(0).getCurrency());
        assertEquals("Nike", response.getOperations().get(0).getMerchant());

        verify(statementService, times(1)).getOperationOnId(customerId);
    }

    @Test
    void addOperation() {
        // Arrange
        OperationDTO operationDTO = new OperationDTO(3, 5050, "Rub", "Nike");

        // Act
        operationsController.addOperation(operationDTO);

        // Assert
        verify(statementService, times(1)).saveOperation(any(Operation.class));
    }

    @Test
    void deleteOperation() {
        // Arrange
        int customerId = 1;

        // Act
        operationsController.deleteOperation(customerId);

        // Assert
        verify(statementService, times(1)).removeOperationsOnCustomerId(customerId);
    }
}