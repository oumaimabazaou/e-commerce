package com.example.ecommerce.userservice.repository;

import com.example.ecommerce.userservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {
} 