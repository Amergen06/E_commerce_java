import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

class Item {
    private int id;

    public Item() {
        this.id = 0;
    }

    public Item(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

// Derived classes
class Product extends Item {
    private String name;
    private double price;

    public Product() {
        super();
        this.name = " ";
        this.price = 0.0;
    }

    public Product(String name, double price, int productId) {
        super(productId); // Call the constructor of the base class
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product ID: " + getId() + ", Name: " + name + ", Price: $" + price;
    }
}

class Shopper extends Item {
    private String name;
    private String email;

    public Shopper() {
        super();
        this.name = " ";
        this.email = " ";
    }

    public Shopper(String name, String email, int shopperId) {
        super(shopperId);
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Shopper ID: " + getId() + ", Name: " + name + ", Email: " + email;
    }
}


class Order {
    private int orderId;
    private Shopper shopper;
    private List<Product> products;

    public Order() {
        this.orderId = 0;
        this.shopper = new Shopper();
        this.products = new ArrayList<>();
    }

    public Order(int orderId, Shopper shopper, List<Product> products) {
        this.orderId = orderId;
        this.shopper = shopper;
        this.products = products;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
    }

    public double getTotalPrice() {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    public List<Product> filterProductsByPrice(double threshold) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getPrice() > threshold) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Shopper: ").append(shopper.getName()).append(" (").append(shopper.getEmail()).append(")").append("\n");
        sb.append("Products:\n");
        for (Product product : products) {
            sb.append("  - ").append(product.toString()).append("\n");
        }
        sb.append("Total Price: $").append(getTotalPrice());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}

public class Main {
    public static void main(String[] args) {
        Product p1 = new Product("Laptop", 999.99, 101);
        Product p2 = new Product("Headphones", 29.99, 102);
        Product p3 = new Product("Mouse", 14.99, 103);
        Product p4 = new Product("Keyboard", 49.99, 104);

        Shopper s1 = new Shopper("Samat Aidin", "email@example.com", 1);

        List<Product> orderProducts = new ArrayList<>();
        orderProducts.add(p1);
        orderProducts.add(p2);
        orderProducts.add(p3);

        Order order = new Order(1001, s1, orderProducts);

        System.out.println("---- Order Details ----");
        System.out.println(order);

        System.out.println("\nProducts with price greater than $20:");
        List<Product> filteredProducts = order.filterProductsByPrice(20);
        for (Product product : filteredProducts) {
            System.out.println(product);
        }

        orderProducts.sort(Comparator.comparingDouble(Product::getPrice));
        System.out.println("\nProducts sorted by price:");
        for (Product product : orderProducts) {
            System.out.println(product);
        }
    }
}
