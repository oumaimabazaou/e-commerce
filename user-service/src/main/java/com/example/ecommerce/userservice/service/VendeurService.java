package com.example.ecommerce.userservice.service;

import com.example.ecommerce.userservice.entity.Vendeur;
import com.example.ecommerce.userservice.repository.VendeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendeurService {
    @Autowired
    private VendeurRepository vendeurRepository;

    public List<Vendeur> findAll() {
        return vendeurRepository.findAll();
    }

    public Optional<Vendeur> findById(String id) {
        return vendeurRepository.findById(id);
    }

    public Vendeur save(Vendeur vendeur) {
        return vendeurRepository.save(vendeur);
    }

    public void deleteById(String id) {
        vendeurRepository.deleteById(id);
    }
} 