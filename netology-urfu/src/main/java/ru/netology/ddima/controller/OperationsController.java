package ru.netology.ddima.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.netology.ddima.controller.dto.OperationDTO;
import ru.netology.ddima.controller.dto.GetOperationResponse;
import ru.netology.ddima.domain.Operation;
import ru.netology.ddima.service.CustomerService;
import ru.netology.ddima.service.StatementService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/operations")
@RequiredArgsConstructor
public class OperationsController {
    private final CustomerService customerService;
    private final StatementService statementService;

    @GetMapping("{id}")
    public GetOperationResponse getOperationsByUser(@PathVariable("id") int customerId){
        List<Operation> operations = statementService.getOperationOnId(customerId);
        List<OperationDTO> operationsDTOS = operations.stream()
                .map(operation -> new OperationDTO(operation.getId(), operation.getSum(), operation.getCurrency(), operation.getMerchant()))
                .collect(Collectors.toList());
        return new GetOperationResponse(operationsDTOS);
    }

    private List<OperationDTO> convertToOperationDTOs(Map<Integer, List<Operation>> operations) {
        List<OperationDTO> operationDTOs = new ArrayList<>();
        for (Map.Entry<Integer, List<Operation>> entry : operations.entrySet()) {
            int customerId = entry.getKey();
            List<Operation> customerOperations = entry.getValue();

            for (Operation operation : customerOperations) {
                OperationDTO operationDTO = new OperationDTO(
                        customerId,
                        operation.getSum(),
                        operation.getCurrency(),
                        operation.getMerchant()
                );
                operationDTOs.add(operationDTO);
            }
        }
        return operationDTOs;
    }
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOperation(@RequestBody OperationDTO operationDTO) {
        Operation operation = convertToOperation(operationDTO);
        statementService.saveOperation(operation);
    }
    private Operation convertToOperation(OperationDTO operationDTO) {
        return new Operation(
                operationDTO.getId(),
                operationDTO.getSum(),
                operationDTO.getCurrency(),
                operationDTO.getMerchant()
        );
    }
    @DeleteMapping("/{id}")
    public void deleteOperation(@PathVariable("id") int id){
        statementService.removeOperationsOnCustomerId(id);
    }
}
