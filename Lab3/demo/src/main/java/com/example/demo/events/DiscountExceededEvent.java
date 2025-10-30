package com.example.demo.events;

import org.springframework.context.ApplicationEvent;

public class DiscountExceededEvent extends ApplicationEvent
{
    private final double amount;
    public DiscountExceededEvent(Object source, double amount)
    {
        super(source);
        this.amount = amount;
    }

    public double getAmount()
    {
        return amount;
    }
}
