package com.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Singleton class for storing and managing products in a warehouse.
 * Uses named instances for multiple warehouses.
 */
public class Warehouse{
    //Map to store warehouse objects by name (singleton pattern)
    private static final Map<String, Warehouse> INSTANCES = new HashMap<>();
    //List to store products in the warehouse
    private final List<Product> products;
    //Set to track products with updated prices
    private final Set<Product> changedProducts = new HashSet<>();
    //Name of the warehouse
    private final String name;

    /**
     * Private constructor for singleton pattern.
     * @param name Name of the warehouse.
     */
    private Warehouse(String name){
        this.name = name;
        this.products = new ArrayList<>();
    }

    /**
     * Returns the warehouse instance for the given name.
     * @param name Name of the warehouse.
     * @return Warehouse instance.
     */
    public static Warehouse getInstance(String name){
        return INSTANCES.computeIfAbsent(name, _ -> new Warehouse(name));
    }

    /**
     * Returns the default warehouse instance.
     * @return Default Warehouse instance.
     */
    public static Warehouse getInstance(){
        return getInstance("DefaultWarehouse");
    }

    //Clears all products and changed products from the warehouse.
    public void clearProducts(){
        products.clear();
        changedProducts.clear();
    }

    //Checks if the warehouse has no products.
    public boolean isEmpty(){
        return products.isEmpty();
    }

    //Returns an unmodifiable list of all products in the warehouse.
    public List<Product> getProducts(){
        return Collections.unmodifiableList(products);
    }

    /**
     * Adds a product to the warehouse.
     * @param product Product to add.
     * @throws IllegalArgumentException if product is null or has a duplicate ID.
     */
    public void addProduct(Product product){
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }

        //Check for duplicate ID
        boolean duplicateIdExists = products.stream()
                        .anyMatch(p->p.uuid().equals(product.uuid()));

        if (duplicateIdExists) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        products.add(product);
    }

    //Removes a product from the warehouse by its UUID.
    public void remove(UUID uuid){
        products.removeIf(product->product.uuid().equals(uuid));
    }

    /**
     * Updates a product's price and tracks it as changed.
     * @param uuid UUID of the product to update.
     * @param newPrice New price for the product.
     * @throws NoSuchElementException if product is not found.
     */
    public void updateProductPrice(UUID uuid, BigDecimal newPrice){
        //Find the product by UUID
        Optional<Product> productOptional = products.stream()
                .filter(p->p.uuid().equals(uuid))
                .findFirst();

        //If the product doesn't exist, throw an exception
        if(productOptional.isEmpty()){
            throw new NoSuchElementException("Product not found with id: " + uuid);
        }

        //Update the product's price and track it as changed
        Product product = productOptional.get();
        product.price(newPrice);
        changedProducts.add(product);
    }

    //Returns an unmodifiable list of products with updated prices.
    public List<Product> getChangedProducts(){
        return List.copyOf(changedProducts);
    }

    //Finds and returns a product by its UUID.
    public Optional<Product> getProductById(UUID id){
        return products.stream()
                .filter(p->p.uuid().equals(id))
                .findFirst();
    }

    //Groups products by their categories and returns a map.
    public Map<Category, List<Product>> getProductsGroupedByCategories(){
        return getProducts().stream()
                .collect(Collectors.groupingBy(Product::category));
    }

    //Returns a list of expired, perishable products.
    public List<Perishable> expiredProducts(){
       return products.stream()
                .filter(p-> p instanceof Perishable)
                .map(p -> (Perishable)p)
                .filter(Perishable::isExpired)
                .collect(Collectors.toList());
    }

    //Returns a list of shippable products.
    public List<Shippable> shippableProducts(){
        return products.stream()
                .filter(p-> p instanceof Shippable)
                .map(p ->(Shippable) p)
                .collect(Collectors.toList());
    }

}
