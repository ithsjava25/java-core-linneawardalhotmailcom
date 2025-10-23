package com.example;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Product{
    //Fields
    private final UUID id;
    private final String name;
    private final Category category;
    private BigDecimal price;

    //Constructor
    protected Product(UUID id, String name, Category category, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    //Concrete getters
    public UUID uuid() {
        return this.id;
    }
    public String name() {
        return this.name;
    }
    public BigDecimal price() {
        return this.price;
    }
    public Category category() {
        return this.category;
    }

    //Concrete setter
    public void price(BigDecimal price){
        this.price = price;
    }

    //Abstract method (subclasses must implement)
    public abstract String productDetails();

}

