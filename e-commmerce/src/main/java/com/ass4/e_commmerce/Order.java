import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    public Order() {}

    public Order(Shopper shopper, List<Product> products) {
        this.shopper = shopper;
        this.products = products;
    }

    public int getId() { return id; }
    public Shopper getShopper() { return shopper; }
    public void setShopper(Shopper shopper) { this.shopper = shopper; }
    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}
