package com.example.demo.service;

import com.example.demo.domain.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("loyal")
public class LoyalDiscountService implements DiscountService {

    @PostConstruct
    public void init() {
        System.out.println("LoyalDiscountService creat! Profil activ: loyal");
    }
    @Override
    public double applyDiscount(Order order)
    {
        double discount = order.getAmount() * 0.1; // 10% discount
        order.setAmount(order.getAmount() - discount);
        return discount;
    }

}
