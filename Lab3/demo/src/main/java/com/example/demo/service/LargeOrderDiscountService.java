package com.example.demo.service;

import com.example.demo.domain.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("largeOrder")
public class LargeOrderDiscountService implements DiscountService {
    @Override
    public double applyDiscount(Order order)
    {
        double discount = order.getAmount() > 1000 ? 50 : 0; // fixed discount
        order.setAmount(order.getAmount() - discount);
        return discount;
    }
}
