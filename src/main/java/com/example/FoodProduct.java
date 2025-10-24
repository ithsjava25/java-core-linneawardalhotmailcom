package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

/**
 * FoodProduct class - represents food products in the warehouse
 * Inherits from Product and implements Perishable and Shippable interface
 */
public class FoodProduct extends Product implements Perishable, Shippable{
    //Expiration date of the food product and the weight in kg
    private final LocalDate expirationDate;
    private final BigDecimal weight;

    private static final BigDecimal SHIPPING_RATE_PER_KG = new BigDecimal("50");

    /**
     * Creates a FoodProduct with the given attributes.
     * @param id Unique identifier for the product.
     * @param name Name of the product.
     * @param category Category of the product.
     * @param price Price of the product (must be >= 0).
     * @param expirationDate Expiration date of the food product.
     * @param weight Weight of the product in kg (must be >= 0).
     * @throws IllegalArgumentException if price or weight is negative.
     */
    public FoodProduct(UUID id, String name, Category category, BigDecimal price,
                       LocalDate expirationDate, BigDecimal weight) {

        //Validate price
        if(price.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        super(id, name, category, price);

        //Validate weight
        if (weight.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Weight cannot be negative.");
        }

        //Assign fields
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    //Returns product details as a formatted string including expiration date
    @Override
    public String productDetails(){
        return String.format("Food: %s, Expires: %s", name(), expirationDate);
    }

    //Getter for expiration date (Perishable interface)
    public LocalDate expirationDate(){
        return expirationDate;
    }
    //Returns weight as a double (Shippable interface)
    @Override
    public double weight(){
        return weight.doubleValue();
    }
    //Calculates shipping cost based on the shipping rule: weight * 50
    @Override
    public BigDecimal calculateShippingCost(){
        return weight.multiply(SHIPPING_RATE_PER_KG)
                .setScale(2, RoundingMode.HALF_UP); //Round to 2 decimal places
    }


}
