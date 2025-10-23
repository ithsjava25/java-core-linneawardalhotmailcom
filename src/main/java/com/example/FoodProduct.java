package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class FoodProduct extends Product implements Perishable, Shippable{
    //Fields specific to FoodProduct
    private final LocalDate expirationDate;
    private final BigDecimal weight;

    //Constructor
    public FoodProduct(UUID id, String name, Category category, BigDecimal price,
                       LocalDate expirationDate, BigDecimal weight) {

        //Validate that price is not negative
        if(price.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        //Call the parent constructor to set inherited fields. UUID.randomUUID creates a unique id
        //super(UUID.randomUUID(), name, category, price);

        super(id, name, category, price);

        //Validate that weight is not negative
        if (weight.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Weight cannot be negative.");
        }

        //Assign fields
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    //Implement abstract method from Product
    @Override
    public String productDetails(){
        return String.format("Food: %s, Expires: %s", name(), expirationDate);
    }

    //Add FoodProduct-specific methods(like Perishable/Shippable interfaces)
    public LocalDate expirationDate(){
        return expirationDate;
    }
    //Returns weight as a double
    @Override
    public Double weight(){
        return weight.doubleValue();
        //Since weight is a final field and validated in the constructor, it cannot be null after construction.
        //return weight != null ? weight.doubleValue() : null;
    }
    //Calculates the shipping cost based on the weight of the FoodProduct, based on the shipping rule: weight * 50
    @Override
    public BigDecimal calculateShippingCost(){
        return weight.multiply(new BigDecimal("50"));
        //return weight != null ? weight.multiply(new BigDecimal("50")) : BigDecimal.ZERO;
    }


}
