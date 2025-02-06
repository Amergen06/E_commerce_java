package com.mycompany.ecommerceapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "shoppers")
public class Shopper implements Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // 🔹 Added password

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    public Shopper() {}

    public Shopper(String name, String email, String password, String address, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
