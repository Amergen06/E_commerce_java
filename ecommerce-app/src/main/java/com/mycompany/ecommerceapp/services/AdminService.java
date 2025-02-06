package com.mycompany.ecommerceapp.services;

import com.mycompany.ecommerceapp.models.Admin;
import com.mycompany.ecommerceapp.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    public Admin createAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(Long id, Admin adminDetails) {
        Optional<Admin> existingAdmin = adminRepository.findById(id);
        if (existingAdmin.isPresent()) {
            Admin admin = existingAdmin.get();
            admin.setName(adminDetails.getName());
            admin.setEmail(adminDetails.getEmail());
            admin.setPassword(adminDetails.getPassword());
            admin.setRole(adminDetails.getRole());
            return adminRepository.save(admin);
        }
        return null; // Handle the case where admin is not found
    }

    public boolean deleteAdmin(Long id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
