package com.example.demo.aspect;

import com.example.demo.domain.Order;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;

public class DiscountLoggingAspect {
    @AfterReturning(pointcut = "execution(* com.example.demo.service.DiscountService.applyDiscount(..))", returning = "discount")
    public void logDiscount(JoinPoint joinPoint, Object discount)
    {
        Order order = (Order)joinPoint.getArgs()[0];
        System.out.printf("LOG: Method=%s, Customer=%s, Discount=%.2f%n",
                joinPoint.getSignature().getName(),
                order.getCustomer().getName(),
                discount);
    }
}
