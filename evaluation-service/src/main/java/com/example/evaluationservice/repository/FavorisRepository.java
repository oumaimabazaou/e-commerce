package com.example.evaluationservice.repository;

import com.example.evaluationservice.entity.Favoris;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavorisRepository extends JpaRepository<Favoris, Integer> {
}