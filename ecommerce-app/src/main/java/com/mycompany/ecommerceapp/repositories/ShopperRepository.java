package com.mycompany.ecommerceapp.repositories;

import com.mycompany.ecommerceapp.models.Shopper;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ShopperRepository extends JpaRepository<Shopper, Long> {
    Optional<Shopper> findByEmail(String email); // ✅ Added method for login
}
