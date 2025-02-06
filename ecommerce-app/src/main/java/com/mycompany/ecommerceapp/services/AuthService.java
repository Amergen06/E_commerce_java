package com.mycompany.ecommerceapp.services;

import com.mycompany.ecommerceapp.models.Admin;
import com.mycompany.ecommerceapp.models.Shopper;
import com.mycompany.ecommerceapp.repositories.AdminRepository;
import com.mycompany.ecommerceapp.repositories.ShopperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ShopperRepository shopperRepository;

    public String authenticateUser(String email, String password) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            return "Admin login successful!";
        }

        Optional<Shopper> shopper = shopperRepository.findByEmail(email);
        if (shopper.isPresent() && shopper.get().getPassword().equals(password)) {
            return "Shopper login successful!";
        }

        return "Invalid credentials!";
    }
}
