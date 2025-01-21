import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

abstract class Item {
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

class Product extends Item {
    private String name;
    private double price;

    public Product() {
        super();
        this.name = " ";
        this.price = 0.0;
    }

    public Product(String name, double price, int productId) {
        super(productId);
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

class DatabaseManager {
    private static final String URL = "jdbc:postgresql://localhost:5432/simpledb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1!";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertProduct(Product product) {
        String sql = "INSERT INTO products (name, price) VALUES (?, ?)";
        try (Connection con = connect(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, product.getName());
            pstmt.setDouble(2, product.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateProduct(int id, String name, double price) {
        String sql = "UPDATE products SET name = ?, price = ? WHERE id = ?";
        try (Connection con = connect(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection con = connect(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fetchProducts() {
        String sql = "SELECT * FROM products";
        try (Connection con = connect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " Name: " + rs.getString("name") + " Price: " + rs.getDouble("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertShopper(Shopper shopper) {
        String checkSql = "SELECT COUNT(*) FROM shoppers WHERE email = ?";
        String insertSql = "INSERT INTO shoppers (name, email) VALUES (?, ?)";

        try (Connection con = connect();
             PreparedStatement checkStmt = con.prepareStatement(checkSql);
             PreparedStatement insertStmt = con.prepareStatement(insertSql)) {

            checkStmt.setString(1, shopper.getEmail());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Shopper with email '" + shopper.getEmail() + "' already exists.");
                return; // Prevent duplicate insertion
            }

            insertStmt.setString(1, shopper.getName());
            insertStmt.setString(2, shopper.getEmail());
            insertStmt.executeUpdate();
            System.out.println("Shopper added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void updateShopper(int id, String name, String email) {
        String sql = "UPDATE shoppers SET name = ?, email = ? WHERE id = ?";
        try (Connection con = connect(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteShopper(int id) {
        String sql = "DELETE FROM shoppers WHERE id = ?";
        try (Connection con = connect(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fetchShoppers() {
        String sql = "SELECT * FROM shoppers";
        try (Connection con = connect(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " Name: " + rs.getString("name") + " Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. View Products");
            System.out.println("5. Add Shopper");
            System.out.println("6. Update Shopper");
            System.out.println("7. Delete Shopper");
            System.out.println("8. View Shoppers");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double productPrice = scanner.nextDouble();
                    DatabaseManager.insertProduct(new Product(productName, productPrice, 1));
                    break;
                case 2:
                    System.out.print("Enter product ID to update: ");
                    int prodId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new product name: ");
                    String newProdName = scanner.nextLine();
                    System.out.print("Enter new product price: ");
                    double newProdPrice = scanner.nextDouble();
                    DatabaseManager.updateProduct(prodId, newProdName, newProdPrice);
                    break;
                case 3:
                    System.out.print("Enter product ID to delete: ");
                    int delProdId = scanner.nextInt();
                    DatabaseManager.deleteProduct(delProdId);
                    break;
                case 4:
                    DatabaseManager.fetchProducts();
                    break;
                case 5:
                    System.out.print("Enter shopper name: ");
                    String shopperName = scanner.nextLine();
                    System.out.print("Enter shopper email: ");
                    String shopperEmail = scanner.nextLine();
                    DatabaseManager.insertShopper(new Shopper(shopperName, shopperEmail, 1));
                    break;
                case 6:
                    System.out.print("Enter shopper ID to update: ");
                    int shopperId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter new shopper name: ");
                    String newShopperName = scanner.nextLine();
                    System.out.print("Enter new shopper email: ");
                    String newShopperEmail = scanner.nextLine();
                    DatabaseManager.updateShopper(shopperId, newShopperName, newShopperEmail);
                    break;
                case 7:
                    System.out.print("Enter shopper ID to delete: ");
                    int delShopperId = scanner.nextInt();
                    DatabaseManager.deleteShopper(delShopperId);
                    break;
                case 8:
                    DatabaseManager.fetchShoppers();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}

