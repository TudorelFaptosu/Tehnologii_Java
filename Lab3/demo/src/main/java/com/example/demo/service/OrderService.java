package com.example.demo.service;

import com.example.demo.service.DiscountService;
import com.example.demo.domain.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final DiscountService discountService;


    public OrderService(DiscountService discountService)
    {
        this.discountService = discountService;
    }
    public void processOrder(Order order)
    {

        double discount = discountService.applyDiscount(order);
        System.out.printf("Processed Order %d for %s, discount applied: %.2f%n",
                order.getId(), order.getCustomer().getName(), discount);
    }
}


