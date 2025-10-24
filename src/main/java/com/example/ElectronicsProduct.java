package com.example;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * ElectronicsProduct class - represents electronic products in the warehouse
 * Inherits from Product and implements Shippable interface
 */

public class ElectronicsProduct extends Product implements Shippable {
    //Warranty period in months and weight in kg
    private final int warrantyMonths;
    private final BigDecimal weight;
    //Shipping cost constants
    private static final BigDecimal BASE_SHIPPING_COST = new BigDecimal("79");
    private static final BigDecimal EXTRA_SHIPPING_COST = new BigDecimal("49");
    private static final BigDecimal WEIGHT_THRESHOLD = new BigDecimal("5.0");

    /**
     * Creates an ElectronicsProduct with the given attributes.
     * @param id Unique identifier for the product.
     * @param name Name of the product.
     * @param category Category of the product.
     * @param price Price of the product.
     * @param warrantyMonths Warranty period in months (must be >= 0).
     * @param weight Weight of the product in kg (must be >= 0).
     * @throws IllegalArgumentException if warrantyMonths is negative.
     */
    public ElectronicsProduct(UUID id, String name, Category category, BigDecimal price,
                              int warrantyMonths, BigDecimal weight) {

        super(id, name, category, price);

        //Validate warranty period
        if (warrantyMonths < 0){
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        }
        //Assign fields
        this.warrantyMonths = warrantyMonths;
        this.weight = weight;

    }
    //Returns the weight of the product as a double (for Shippable interface)
    @Override
    public Double weight(){
        return weight.doubleValue();
    }

    /**
     * Calculates shipping cost based on weight
     * @return Base cost (79) plus extra cost (49) if weight > 5.0 kg
     */
    @Override
    public BigDecimal calculateShippingCost(){
        //Shipping rule: base 79, add 49 if weight > 5.0 kg.
        BigDecimal totalCost = BASE_SHIPPING_COST;
        if (weight.compareTo(WEIGHT_THRESHOLD) > 0) {
            totalCost = totalCost.add(EXTRA_SHIPPING_COST);
        }
        return totalCost;
    }
    //Returns a formatted string with product details including warranty
    @Override
    public String productDetails(){
        return String.format("Electronics: %s, Warranty: %d months", name(), warrantyMonths);
    }
    // Getter for warranty period
    public int getWarrantyMonths(){
        return warrantyMonths;
    }
}
