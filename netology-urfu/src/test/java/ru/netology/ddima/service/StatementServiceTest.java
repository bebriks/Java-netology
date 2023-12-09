package ru.netology.ddima.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import ru.netology.ddima.OperationHistoryApiApplicationTest;
import ru.netology.ddima.domain.Operation;
import ru.netology.ddima.service.CustomerService;
import ru.netology.ddima.service.StatementService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatementServiceTest extends OperationHistoryApiApplicationTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private StatementService statementService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOperation() {
        // Arrange
        Operation operation = new Operation(1, 5000, "Rub", "Nike");

        // Act
        statementService.saveOperation(operation);

        // Assert
        Map<Integer, List<Operation>> storage = statementService.getStorage();
        assertTrue(storage.containsKey(1));
        assertEquals(1, storage.get(1).size());
        assertEquals(operation, storage.get(1).get(0));
    }

    @Test
    void getOperations() {
        Map<Integer, List<Operation>> expectedOperations = new HashMap<>();
        expectedOperations.put(1, new ArrayList<>());
        expectedOperations.put(2, new ArrayList<>());
        assertEquals(expectedOperations.toString(), "{1=[], 2=[]}");
    }

    @Test       
    void getOperationOnId_existingId() {
        int operationId = 1;
        List<Operation> expectedOperations = new ArrayList<>();
        expectedOperations.add(new Operation(1, 5000, "Rub", "Nike"));
        statementService.saveOperation(new Operation(1, 5000, "Rub", "Nike"));

        // Act
        List<Operation> operations = statementService.getOperationOnId(operationId);

        // Assert
        assertEquals(expectedOperations, operations);
    }

    @Test
    void getOperationOnId_nonExistingId() {
        // Arrange
        int operationId = 3;

        // Act
        List<Operation> operations = statementService.getOperationOnId(operationId);

        // Assert
        assertNotNull(operations);
        assertTrue(operations.isEmpty());
    }

    @Test
    void removeOperationsOnCustomerId() {
        // Arrange
        int customerId = 1;
        statementService.saveOperation(new Operation(1, 5000, "Rub", "Nike"));

        // Act
        statementService.removeOperationsOnCustomerId(customerId);

        // Assert
        Map<Integer, List<Operation>> storage = statementService.getStorage();
        assertFalse(storage.containsKey(customerId));
    }
}
