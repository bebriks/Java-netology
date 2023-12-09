package ru.netology.ddima.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.ddima.controller.dto.CustomerDTO;
import ru.netology.ddima.controller.dto.GetClientResponse;
import ru.netology.ddima.domain.Customer;
import ru.netology.ddima.service.CustomerService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    List<CustomerDTO> customerDTOS = new ArrayList<>();

    @GetMapping
    public GetClientResponse getClient() {
        List<CustomerDTO> newCustomerDTOs = new ArrayList<>();
        for (Customer customer : customerService.getCustomers()) {
            CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getName());
            newCustomerDTOs.add(customerDTO);
        }

        return new GetClientResponse(newCustomerDTOs);
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") int customerId) {
        for (Customer customer : customerService.getCustomers()) {
            if (customer.getId() == customerId) {
                return new CustomerDTO(customer.getId(), customer.getName());
            }
        }
        return null;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDTO> delCustomer(@PathVariable(name = "id") int customerId) {
        List<Customer> customers = customerService.getCustomers();
        for (Customer customer : customers) {
            if (customer.getId() == customerId) {
                customerService.removeCustomer(customer);
                return new ResponseEntity<>(new CustomerDTO(customer.getId(), customer.getName()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addClient(@Valid @RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer(customerDTO.getId(), customerDTO.getName());
        customerService.addClient(customer);
    }
}
