package com.example.demo.aspect;

import com.example.demo.domain.Order;
import com.example.demo.exception.CustomerNotEligibleException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repository.CustomerRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DiscountValidationAspect {
    private final CustomerRepository customerRepository;

    public DiscountValidationAspect(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    @Before("execution(* com.example.demo.service.DiscountService.applyDiscount(..)) &&  args(order)")
    public void checkEligibility(Order order)
    {

        if(!customerRepository.existsById(order.getCustomer().getId()))
        {
            throw new CustomerNotFoundException("Customer not found");
        }
        if (!order.getCustomer().getEligible()) {
            throw new CustomerNotEligibleException("Customer not eligible");
        }
    }

}
