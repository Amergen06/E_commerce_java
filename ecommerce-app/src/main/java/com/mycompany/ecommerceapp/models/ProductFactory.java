package com.mycompany.ecommerceapp.models;

public class ProductFactory {

    public static Product createProduct(String name, String description, double price, int stockQuantity) {
        return new Product(name, description, price, stockQuantity);
    }
}
