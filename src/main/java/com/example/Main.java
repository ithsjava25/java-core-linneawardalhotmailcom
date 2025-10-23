package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public class Main {
     static void main(String[] args) {

         //Create a warehouse
         Warehouse warehouse = Warehouse.getInstance("TestWarehouse");

         //Add a product
         Product milk = new FoodProduct(
                 UUID.randomUUID(),
                 "Milk",
                 Category.of("Dairy"),
                 new BigDecimal("15.50"),
                 LocalDate.now().plusDays(7),
                 new BigDecimal("1.0")
         );

         warehouse.addProduct(milk);

         //Update the product's price
         IO.println("Updating milk price...");
         warehouse.updateProductPrice(milk.uuid(), new BigDecimal("17.00"));

         //Print the changed products
         IO.println("Changed products: " + warehouse.getChangedProducts());



     }
}
