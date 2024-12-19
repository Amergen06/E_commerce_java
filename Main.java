class Product {
    private String name;
    private double price;
    private int productId;
    
    public Product() {
        this.name = " ";
        this.price = 0.0;
        this.productId = 0;
    }
    
    public Product(String name, double price, int productId) {
        this.name = name;
        this.price = price;
        this.productId = productId;
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
    
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public String toString() {
        return "Product ID: " + productId + ", Name: " + name + ", Price: $" + price;
    }
}
class Shopper {
    private String name;
    private String email;
    private int shopperId;
    
    // Default constructor
    public Shopper() {
        this.name = " ";
        this.email = " ";
        this.shopperId = 0;
    }
    
    // Parameterized constructor
    public Shopper(String name, String email, int shopperId) {
        this.name = name;
        this.email = email;
        this.shopperId = shopperId;
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
    
    public int getShopperId() {
        return shopperId;
    }
    
    public void setShopperId(int shopperId) {
        this.shopperId = shopperId;
    }
    
    public String toString() {
        return "Shopper ID: " + shopperId + ", Name: " + name + ", Email: " + email;
    }
}
class Order {
    private int orderId;
    private Shopper shopper;
    private Product[] products; 
    
    public Order() {
        this.orderId = 0;
        this.shopper = new Shopper();
        this.products = new Product[0]; // empty array
    }
    
    // Parameterized constructor
    public Order(int orderId, Shopper shopper, Product[] products) {
        this.orderId = orderId;
        this.shopper = shopper;
        this.products = products;
    }
    
    // Getters and Setters
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
    
    public Product[] getProducts() {
        return products;
    }
    
    public void setProducts(Product[] products) {
        this.products = products;
    }
    
    public double getTotalPrice() {
        double total = 0.0;
        for (int i = 0; i < products.length; i++) {
            total += products[i].getPrice();
        }
        return total;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Shopper: ").append(shopper.getName()).append(" (").append(shopper.getEmail()).append(")").append("\n");
        sb.append("Products:\n");
        for (int i = 0; i < products.length; i++) {
            sb.append("  - ").append(products[i].toString()).append("\n");
        }
        sb.append("Total Price: $").append(getTotalPrice());
        return sb.toString();
    }
}
public class Main {
    public static void main(String[] args) {
        Product p1 = new Product("Laptop", 999.99, 101);
        Product p2 = new Product("Headphones", 29.99, 102);
        Product p3 = new Product("Mouse", 14.99, 103);
        
        Shopper s1 = new Shopper("Samat Aidin", "email@example.com", 1);
        
        Product[] order1Products = {p1, p2};
        Order order1 = new Order(1001, s1, order1Products);
        
        System.out.println("---- Order 1 Details ----");
        System.out.println(order1.toString());
        
        Product[] order2Products = {p3};
        Order order2 = new Order(1002, s1, order2Products);
        
        System.out.println("\n---- Order 2 Details ----");
        System.out.println(order2.toString());
        
        double order1Total = order1.getTotalPrice();
        double order2Total = order2.getTotalPrice();
        
        System.out.println("\nComparing Order 1 and Order 2 by total price:");
        if (order1Total > order2Total) {
            System.out.println("Order 1 is more expensive than Order 2 by");
            System.out.println(order1Total-order2Total);
        } else if (order1Total < order2Total) {
            System.out.println("Order 2 is more expensive than Order 1 by");
            System.out.println(order2Total-order1Total);
        } else {
            System.out.println("Order 1 and Order 2 have the same total price.");
        }
    }
}
