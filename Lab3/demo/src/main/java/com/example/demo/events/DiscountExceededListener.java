package com.example.demo.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DiscountExceededListener
{
    @EventListener
    public void onDiscountExceeded(DiscountExceededEvent event)
    {
        System.out.println("Discount exceeded");
    }
}
