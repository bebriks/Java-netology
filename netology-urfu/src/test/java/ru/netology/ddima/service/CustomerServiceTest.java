package ru.netology.ddima.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import ru.netology.ddima.OperationHistoryApiApplicationTest;
import ru.netology.ddima.domain.Customer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CustomerServiceTest extends OperationHistoryApiApplicationTest{
    @Autowired
    CustomerService customerService;

    @Test
    public void getClientsTest(){
        List<Customer> customers = customerService.getCustomers();
        Customer customer1 = customers.get(0);
        Customer customer2 = customers.get(1);
        assertEquals(1, customer1.getId());
        assertEquals(2, customer2.getId());

        assertEquals("Spring", customer1.getName());
        assertEquals("Boot", customer2.getName());

        assertEquals(2, customers.size());
    }
    @Nested
    class CustomerServiceTest1 extends OperationHistoryApiApplicationTest {
        @Test
        void addClientShouldAddCustomerToStorage() {
            Customer customer = new Customer(4, "NewCustomer");
            customerService.addClient(customer);
            assertTrue(customerService.getCustomers().contains(customer));
        }

        @Test
        void getCustomersShouldReturnAllCustomersInStorage() {
            Customer customer1 = new Customer(6, "Customer1");
            Customer customer2 = new Customer(7, "Customer2");
            customerService.addClient(customer1);
            customerService.addClient(customer2);
            List<Customer> customers = customerService.getCustomers();
            assertTrue(customers.contains(customer1));
            assertTrue(customers.contains(customer2));
            assertEquals(4, customers.size());
            customerService.removeCustomer(customer1);
            customerService.removeCustomer(customer2);
            assertEquals(2, customers.size());
        }

        @Test
        void removeCustomerShouldRemoveCustomerFromStorage() {
            Customer customer1 = new Customer(5, "CustomerToRemove");
            customerService.addClient(customer1);
            customerService.removeCustomer(customer1);
            assertFalse(customerService.getCustomers().contains(customer1));
        }

        @Test
        void initShouldInitializeStorageWithDefaultCustomers() {
            List<Customer> customers = customerService.getCustomers();
            assertEquals(2, customers.size());
        }
    }
}

