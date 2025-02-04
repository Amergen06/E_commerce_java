import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/shoppers")
public class ShopperController {

    @Autowired
    private ShopperRepository shopperRepository;

    @GetMapping
    public List<Shopper> getAllShoppers() {
        return shopperRepository.findAll();
    }

    @PostMapping
    public Shopper createShopper(@RequestBody Shopper shopper) {
        return shopperRepository.save(shopper);
    }

    @PutMapping("/{id}")
    public Shopper updateShopper(@PathVariable int id, @RequestBody Shopper updatedShopper) {
        return shopperRepository.findById(id)
                .map(shopper -> {
                    shopper.setName(updatedShopper.getName());
                    shopper.setEmail(updatedShopper.getEmail());
                    return shopperRepository.save(shopper);
                })
                .orElseThrow(() -> new RuntimeException("Shopper not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteShopper(@PathVariable int id) {
        shopperRepository.deleteById(id);
    }
}
