package com.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/** Warehouse class - used to store and manage products
 * Contain a collection of Product objects (e.g a List<Product>) */

public class Warehouse{
    //Fields
    private static final Map<String, Warehouse> INSTANCES = new HashMap<>();
    private final List<Product> products;
    //Field to track changed products
    private final Set<Product> changedProducts = new HashSet<>();
    private final String name;

    //Private constructor for singleton pattern
    private Warehouse(String name){
        this.name = name;
        this.products = new ArrayList<>();
    }

    //getInstance(String name) returns the same instance per unique name.
    //Factory method to get or create a warehouse instance
    public static Warehouse getInstance(String name){
        return INSTANCES.computeIfAbsent(name, _ -> new Warehouse(name));
    }
    //Default instance
    public static Warehouse getInstance(){
        return getInstance("DefaultWarehouse");
    }

    public void clearProducts(){
        products.clear();
    }

    public boolean isEmpty(){
        return products.isEmpty();
    }

    public List<Product> getProducts(){
        return Collections.unmodifiableList(products);
    }


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

    //remove(UUID): remove the matching product if present.
    public void remove(UUID uuid){
        products.removeIf(product->product.uuid().equals(uuid));

    }

    public void updateProductPrice(UUID uuid, BigDecimal newPrice){
        //Find the product by UUID
        Optional<Product> productOptional = products.stream()
                .filter(p->p.uuid().equals(uuid))
                .findFirst();

        //If the product doesn't exist, throw an exception
        if(productOptional.isEmpty()){
            throw new NoSuchElementException("Product not found with id: " + uuid);
        }

        //Update the product's price
        Product product = productOptional.get();
        product.price(newPrice);

        //Track the product as changed
        changedProducts.add(product);

    }

    public List<Product> getChangedProducts(){
        return List.copyOf(changedProducts);
    }

    /*Search the products list for a product with the given UUID
    * Return the product (wrapped in an Optional) if found
    * Return Optional.empty() if the product is not found*/
    public Optional<Product> getProductById(UUID id){
        return products.stream()
                .filter(p->p.uuid().equals(id))
                .findFirst();
    }

    public Map<Category, List<Product>> getProductsGroupedByCategories(){
        return getProducts().stream()
                .collect(Collectors.groupingBy(Product::category));
    }

    //Method that return List<Perishable> that are expired
    public List<Perishable> expiredProducts(){
       return products.stream()
                .filter(p-> p instanceof Perishable)
                .map(p -> (Perishable)p)
                .filter(Perishable::isExpired)
                .collect(Collectors.toList());
    }

    //Method that filters and returns only the products that are objects of Shippable
    public List<Shippable> shippableProducts(){
        return products.stream()
                .filter(p-> p instanceof Shippable)
                .map(p ->(Shippable) p)
                .collect(Collectors.toList());
    }

}
