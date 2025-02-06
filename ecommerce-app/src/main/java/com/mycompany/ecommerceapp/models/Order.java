package com.mycompany.ecommerceapp.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "shopper_id", nullable = false)
    private Shopper shopper;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @Column(name = "total_price")
    private double totalPrice;

    public Order() {}

    private Order(Shopper shopper, List<Product> products) {
        this.shopper = shopper;
        this.products = products;
        this.totalPrice = calculateTotalPrice();
    }

    public static Order createOrder(Shopper shopper, List<Product> products) {
        return new Order(shopper, products);
    }

    private double calculateTotalPrice() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public Long getId() {
        return id;
    }

    public Shopper getShopper() {
        return shopper;
    }

    public void setShopper(Shopper shopper) {
        this.shopper = shopper;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        this.totalPrice = calculateTotalPrice(); // Recalculate total price
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", shopper=" + shopper.getName() +
                ", totalPrice=$" + totalPrice +
                ", products=" + products +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(shopper, order.shopper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shopper);
    }
}
