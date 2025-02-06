package com.mycompany.ecommerceapp.repositories;

import com.mycompany.ecommerceapp.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email); // ✅ Added method for login
}
