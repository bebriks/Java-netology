package ru.netology.ddima.service;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.netology.ddima.controller.dto.CustomerDTO;
import ru.netology.ddima.domain.Customer;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Component
public class CustomerService {
    private final List<Customer> storage = new ArrayList<>();

    public void addClient(Customer customer) {
        storage.add(customer);
    }

    public void removeCustomer(Customer customer) {
        storage.remove(customer);
    }
    public List<Customer> getCustomers() {
        return storage;
    }
    @PostConstruct
    public void init(){
        storage.add(new Customer(1, "Spring"));
        storage.add(new Customer(2, "Boot"));
    }
}
