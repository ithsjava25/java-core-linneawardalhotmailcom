package com.example;

import java.math.BigDecimal;

/**
 * Interface for products that can be shipped.
 */
public interface Shippable {
    /**
     * Returns the weight of the product in kg.
     * @return Weight as a double.
     */
    Double weight();

    /**
     * Calculates the shipping cost for the product.
     * @return Shipping cost as BigDecimal.
     */
    BigDecimal calculateShippingCost();
}
