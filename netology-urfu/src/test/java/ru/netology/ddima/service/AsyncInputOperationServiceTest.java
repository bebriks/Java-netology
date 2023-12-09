package ru.netology.ddima.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import ru.netology.ddima.domain.Operation;

import static org.mockito.Mockito.mock;

@SpringBootTest
class AsyncInputOperationServiceTest {
    @Autowired
    private AsyncInputOperationService service;

    @Autowired
    private StatementService statementService;

    @Test
    public void testAddAndProcessOperation() throws InterruptedException {
        Operation operation = new Operation(1, 1000, "USD", "Amazon");

        service.addOperation(operation);
        Thread.sleep(100); // Wait for processing

        Assert.isTrue(service.operations.isEmpty());
        // Verify operation is saved in the statement service
        Assert.notNull(statementService.getOperationOnId(operation.getId()));
    }
    @Test
    public void testEmptyQueueHandling() throws InterruptedException {

        long startTime = System.currentTimeMillis();
        service.startProcessing();
        Thread.sleep(100); // Wait for processing

        long elapsedTime = System.currentTimeMillis() - startTime;
        Assert.isTrue(elapsedTime >= service.properties.getSleepMilliSeconds());
    }

    @Test
    public void testQueueOverflow() {
        for (int i = 0; i < 100; i++) { // Fill the queue beyond capacity
            service.addOperation(new Operation(i, 100, "USD", "Test"));
        }
    }

}