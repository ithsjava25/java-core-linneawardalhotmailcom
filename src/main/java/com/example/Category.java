package com.example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class Category {
    //Fields
    private final String name;
    private static final Map<String, Category> CACHE = new HashMap<>();

    // Private constructor
    private Category(String name) {
        this.name = name;
    }

    // Factory method
    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name can't be blank");
        }

        String formattedName = Arrays.stream(name.trim().split("\\s+"))
                .map(word->word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
        return CACHE.computeIfAbsent(formattedName, Category::new);
    }
    // Getter for the name
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Category other = (Category) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}
