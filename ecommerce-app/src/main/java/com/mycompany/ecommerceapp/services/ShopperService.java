package com.mycompany.ecommerceapp.services;

import com.mycompany.ecommerceapp.models.Shopper;
import com.mycompany.ecommerceapp.repositories.ShopperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopperService {

    @Autowired
    private ShopperRepository shopperRepository;

    public List<Shopper> getAllShoppers() {
        return shopperRepository.findAll();
    }

    public Optional<Shopper> getShopperById(Long id) {
        return shopperRepository.findById(id);
    }

    public Shopper createShopper(Shopper shopper) {
        return shopperRepository.save(shopper);
    }

    public Shopper updateShopper(Long id, Shopper shopperDetails) {
        return shopperRepository.findById(id).map(shopper -> {
            shopper.setName(shopperDetails.getName());
            shopper.setEmail(shopperDetails.getEmail());
            return shopperRepository.save(shopper);
        }).orElseThrow(() -> new RuntimeException("Shopper not found"));
    }

    public boolean deleteShopper(Long id) {
        if (shopperRepository.existsById(id)) {
            shopperRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
