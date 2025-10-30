package com.example.demo.repository;

import com.example.demo.domain.Customer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomerRepository {

    Map<Long, Customer> customers = new HashMap<>();

    public CustomerRepository()
    {

        customers.put(1L, new Customer(1L, "Alice", true));
        customers.put(2L, new Customer(2L, "Bob", false));
    }

    public Customer findById(Long id) { return customers.get(id); }
    public boolean existsById(Long id) { return customers.containsKey(id);}

}
