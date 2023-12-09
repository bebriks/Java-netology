package ru.netology.ddima.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.netology.ddima.domain.Customer;
import ru.netology.ddima.domain.Operation;

import javax.annotation.PostConstruct;
import java.util.*;

@AllArgsConstructor
@Component
public class StatementService {
    private CustomerService customerService;
    @Getter
    private final Map<Integer, List<Operation>> storage = new HashMap<>();

    public void saveOperation(Operation operation) {
        List<Operation> operations = storage.computeIfAbsent(operation.getId(), k -> new ArrayList<>());
        operations.add(operation);
    }

    public String getOperations() {
        return storage.toString();
    }
    public List<Operation> getOperationOnId(int operationId) {
        return storage.getOrDefault(operationId, new ArrayList<>());
    }

    public void removeOperationsOnCustomerId(int id) {
        storage.remove(id);
    }
    @PostConstruct
    public void init() {
        List<Operation> operations1 = new ArrayList<>();
        operations1.add(new Operation(1, 5000, "Rub", "Nike"));

        List<Operation> operations2 = new ArrayList<>();
        operations2.add(new Operation(2, 500, "Euro", "Adidas"));

        storage.put(1, operations1);
        storage.put(2, operations2);
    }
}
