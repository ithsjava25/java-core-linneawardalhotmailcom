package com.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class Category {
    // Cache for reusing Category instances with the same name
    private static final Map<String, Category> CACHE = new HashMap<>();
    //Formatted name of the category (e.g "Dairy", "Electronics")
    private final String name;


    // Private constructor to enforce factory method usage
    private Category(String name) {
        this.name = name;
    }

    /**
     * Factory method to create or retrieve a Category object
     * @param name Raw category name (e.g "dairy", "DAIRY PRODUCTS")
     * @return Cached or new Category instance with formatted name (e.g "Dairy")
     * @throws IllegalArgumentException if name is null or blank
     */
    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name can't be blank");
        }
        //Format name: Capitalize first letter of each word (e.g "dairy" -> "Dairy")
        String formattedName = Arrays.stream(name.trim().split("\\s+"))
                .map(word->word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
        //Return existing object from cache or create and store a new one
        return CACHE.computeIfAbsent(formattedName, Category::new);
    }
    // Getter for the formatted category name
    public String getName() {
        return name;
    }

    //Standard equality check based on the category name
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Category other = (Category) obj;
        return name.equals(other.name);
    }
    //Hash code based on the category name
    @Override
    public int hashCode() {
        return name.hashCode();
    }
    //String representation of the category (its name)
    @Override
    public String toString() {
        return name;
    }
}
