package com.mycompany.ecommerceapp.controllers;

import com.mycompany.ecommerceapp.models.Product;
import com.mycompany.ecommerceapp.services.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        log.info("Fetching all products, Total: {}", products.size());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("Fetching product with ID: {}", id);
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        log.info("Creating new product: {}", product.getName());
        Product newProduct = Product.createProduct(
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity()
        );
        newProduct = productService.createProduct(newProduct);
        return ResponseEntity.ok(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        log.info("Updating product ID: {}", id);
        Product updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.warn("Deleting product ID: {}", id);
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public List<Product> searchProductsByName(@RequestParam String name) {
        return productService.searchProductsByName(name);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProductsByPrice(@RequestParam double minPrice, @RequestParam double maxPrice) {
        log.info("Filtering products between ${} and ${}", minPrice, maxPrice);
        List<Product> products = productService.filterProductsByPrice(minPrice, maxPrice);
        return ResponseEntity.ok(products);
    }
}
