package com.mycompany.ecommerceapp.models;

public interface Person {
    Long getId();
    String getName();
    String getEmail();
    String getPassword(); // 🔹 Added password getter
}
