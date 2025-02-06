package com.mycompany.ecommerceapp.controllers;

import com.mycompany.ecommerceapp.models.Shopper;
import com.mycompany.ecommerceapp.services.ShopperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shoppers")
public class ShopperController {

    @Autowired
    private ShopperService shopperService;

    @GetMapping
    public List<Shopper> getAllShoppers() {
        return shopperService.getAllShoppers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shopper> getShopperById(@PathVariable Long id) {
        Optional<Shopper> shopper = shopperService.getShopperById(id);
        return shopper.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Shopper> createShopper(@RequestBody Shopper shopper) {
        Shopper newShopper = shopperService.createShopper(shopper);
        return ResponseEntity.ok(newShopper);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shopper> updateShopper(@PathVariable Long id, @RequestBody Shopper shopperDetails) {
        Shopper updatedShopper = shopperService.updateShopper(id, shopperDetails);
        return ResponseEntity.ok(updatedShopper);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShopper(@PathVariable Long id) {
        boolean deleted = shopperService.deleteShopper(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
