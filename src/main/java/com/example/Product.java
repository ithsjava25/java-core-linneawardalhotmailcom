package com.example;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Abstract base class representing a product with common attributes and behavior.
 */
public abstract class Product{
    //Unique identifier for each product
    private final UUID id;
    //Name of the product
    private final String name;
    //Category of the product
    private final Category category;
    //Price of the product (can be updated)
    private BigDecimal price;

    /**
     * Creates a Product with the given attributes.
     * @param id Unique identifier for the product.
     * @param name Name of the product.
     * @param category Category of the product.
     * @param price Price of the product.
     */
    protected Product(UUID id, String name, Category category, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    //Getter for product ID
    public UUID uuid() {
        return this.id;
    }
    //Getter for product name
    public String name() {
        return this.name;
    }
    //Getter for product price
    public BigDecimal price() {
        return this.price;
    }
    //Getter for product category
    public Category category() {
        return this.category;
    }
    //Setter for product price
    public void price(BigDecimal price){
        this.price = price;
    }

    //Abstract method to get product details as a string (to be implemented by subclasses)
    public abstract String productDetails();

}

