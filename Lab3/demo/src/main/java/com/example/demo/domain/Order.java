package com.example.demo.domain;

public class Order {
    private Long id;
    private Customer customer;
    private double amount;

    public Order(Long id, Customer customer, double amount)
    {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }
}
