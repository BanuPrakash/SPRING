package com.cisco.vehiclerental.client;

import com.cisco.vehiclerental.entity.Customer;
import com.cisco.vehiclerental.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
@RequiredArgsConstructor
public class CustomerClient implements CommandLineRunner {
    private final RentalService service;

    @Override
    public void run(String... args) throws Exception {
        addCustomers();
        listCustomers();
    }
    public void addCustomers() {
        if(service.getCustomerCount() == 0) {
            service.addCustomer(Customer.builder().email("anne@cisco.com")
                        .firstName("Anne")
                        .lastName("Hathaway").build());
            service.addCustomer(Customer.builder().email("roger@cisco.com")
                    .firstName("Roger")
                    .lastName("Smith").build());
        }
    }
    private void listCustomers() {
        List<Customer> customers = service.getCustomers();
        for (Customer c: customers) {
            System.out.println(c);
        }
    }
}
