package com.example.ecommerce.userservice.service;

import com.example.ecommerce.userservice.entity.Livreur;
import com.example.ecommerce.userservice.repository.LivreurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivreurService {
    @Autowired
    private LivreurRepository livreurRepository;

    public List<Livreur> findAll() {
        return livreurRepository.findAll();
    }

    public Optional<Livreur> findById(String id) {
        return livreurRepository.findById(id);
    }

    public Livreur save(Livreur livreur) {
        return livreurRepository.save(livreur);
    }

    public void deleteById(String id) {
        livreurRepository.deleteById(id);
    }
} 