package com.example;

import java.time.LocalDate;

/**
 * Interface for products that can expire.
 */
public interface Perishable {
    /**
     * Returns the expiration date of the product.
     * @return Expiration date as LocalDate.
     */
    LocalDate expirationDate();

    /**
     * Checks if the product is expired.
     * @return true if the current date is after the expiration date, false otherwise.
     */
    default boolean isExpired() {
        return expirationDate().isBefore(LocalDate.now());
    }

}

