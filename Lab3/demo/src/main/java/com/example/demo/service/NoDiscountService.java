package com.example.demo.service;

import com.example.demo.domain.Order;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class NoDiscountService implements DiscountService {

    @Override
    public double applyDiscount(Order order)
    {
        return 0;
    }
}
