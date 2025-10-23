package com.example;

import java.math.BigDecimal;
import java.util.UUID;

public class ElectronicsProduct extends Product implements Shippable {
    //Fields specific to ElectronicsProduct
    private final int warrantyMonths;
    private final BigDecimal weight;
    private static final BigDecimal BASE_SHIPPING_COST = new BigDecimal("79");
    private static final BigDecimal EXTRA_SHIPPING_COST = new BigDecimal("49");
    private static final BigDecimal WEIGHT_THRESHOLD = new BigDecimal("5.0");

    //Constructor
    public ElectronicsProduct(UUID id, String name, Category category, BigDecimal price,
                              int warrantyMonths, BigDecimal weight) {
        //Call the parent constructor to set inherited fields. UUID.randomUUID creates a unique id
        super(UUID.randomUUID(), name, category, price);

        //Validate that warrantyMonth isn't negative
        if (warrantyMonths < 0){
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        }
        //Assign fields
        this.warrantyMonths = warrantyMonths;
        this.weight = weight;

    }

    @Override
    public Double weight(){
        return weight.doubleValue();
        //return weight != null ? weight.doubleValue() : null;
    }
    @Override
    public BigDecimal calculateShippingCost(){
        //Shipping rule: base 79, add 49 if weight > 5.0 kg.
        BigDecimal totalCost = BASE_SHIPPING_COST;
        if (weight.compareTo(WEIGHT_THRESHOLD) > 0) {
            totalCost = totalCost.add(EXTRA_SHIPPING_COST);
        }
        return totalCost;
        //return weight != null ? weight.multiply(new BigDecimal("100")) : BigDecimal.ZERO;
    }
    @Override
    public String productDetails(){
        return String.format("Electronics: %s, Warranty: %d months", name(), warrantyMonths);
    }

    public int getWarrantyMonths(){
        return warrantyMonths;
    }
}
