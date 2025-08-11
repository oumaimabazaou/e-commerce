package com.example.commandeservice.repository;

import com.example.commandeservice.entity.ShippingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingInfoRepository extends JpaRepository<ShippingInfo, Integer> {
}