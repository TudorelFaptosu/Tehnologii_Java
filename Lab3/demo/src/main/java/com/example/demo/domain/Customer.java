package com.example.demo.domain;

public class Customer {
    private Long id;
    private String name;
    private boolean eligible;

    public Customer(Long id, String name,  boolean eligible)
    {
        this.id = id;
        this.name = name;
        this.eligible = eligible;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean getEligible()
    {
        return eligible;
    }

    public void setEligible(boolean eligible) {
        this.eligible = eligible;
    }

}

