package com.example.demo.exception;

public class CustomerNotEligibleException extends RuntimeException
{
    public CustomerNotEligibleException(String message)
    {
        super(message);
    }
}
