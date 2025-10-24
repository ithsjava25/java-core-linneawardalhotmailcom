package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public class Main {
     public static void main(String[] args) {

         //Create warehouses (demonstrating singleton behavior)
         Warehouse warehouse = Warehouse.getInstance("DemoWarehouse");
         Warehouse warehouse2 = Warehouse.getInstance("DemoWarehouse2");
         IO.println(("Warehouse 1:" + warehouse));
         IO.println(("Warehouse 2:" + warehouse2));
         IO.println("Same instance? " + (warehouse == Warehouse.getInstance("DemoWarehouse")));

         //Add products of different types
         Product milk = new FoodProduct(
                 UUID.randomUUID(),
                 "Milk",
                 Category.of("Dairy"),
                 new BigDecimal("15.50"),
                 LocalDate.now().plusDays(7),
                 new BigDecimal("1.0")
         );
         Product apple = new FoodProduct(
                 UUID.randomUUID(),
                 "Apple",
                 Category.of("Fruit"),
                 new BigDecimal("2.50"),
                 LocalDate.now().plusDays(5),
                 new BigDecimal("0.5")
         );
         Product laptop = new ElectronicsProduct(
                 UUID.randomUUID(),
                 "Laptop",
                 Category.of("Electronics"),
                 new BigDecimal("1599.99"),
                 24,
                 new BigDecimal("2.5")
         );

         warehouse.addProduct(milk);
         warehouse.addProduct(apple);
         warehouse.addProduct(laptop);

         //Print all products
         IO.println("\nAll products in warehouse:");
         warehouse.getProducts().forEach(product->IO.println("-" + product.productDetails()));

         //Update a product's price
         IO.println("\nUpdating milk price...");
         warehouse.updateProductPrice(milk.uuid(), new BigDecimal("17.00"));
         IO.println("New milk price: " + milk.price());

         //Print changed products
         IO.println("\nChanged products: " + warehouse.getChangedProducts());

         //Group products by category
         IO.println("\nProducts grouped by category:");
         warehouse.getProductsGroupedByCategories().forEach((category, products) ->{
             IO.println(category.getName() + ":");
             products.forEach(product-> IO.println(" -" + product.name()));
         });

         //Remove a product
         IO.println("\nRemoving apple...");
         warehouse.remove(apple.uuid());
         IO.println("Products after removal: " + warehouse.getProducts().size());

         //Check for expired products
         IO.println("\nExpired products:");
         warehouse.expiredProducts().forEach(expired-> IO.println("-" + expired));

         //Calculate shipping costs
         IO.println("\nShipping costs for shippable products:");
         warehouse.shippableProducts().forEach(shippable->IO.println("- " + shippable + ": $"
                 + shippable.calculateShippingCost())
         );

         //Demonstrate duplicate ID check
         try{
             IO.println("\nAttempting to add a product with a duplicate ID...");
             Product duplicateMilk = new FoodProduct(
                     milk.uuid(), //Same UUID as existing milk product
                     "Duplicate Milk",
                     Category.of("Dairy"),
                     new BigDecimal("16.00"),
                     LocalDate.now().plusDays(5),
                     new BigDecimal("1.0")
             );
             warehouse.addProduct(duplicateMilk);
         }catch (IllegalArgumentException e){
             IO.println("Caught expected exception: " + e.getMessage());
         }

         //Clear the warehouse
         IO.println("\nClearing warehouse...");
         warehouse.clearProducts();
         IO.println("Warehouse is now empty: " + warehouse.isEmpty());

     }
}
