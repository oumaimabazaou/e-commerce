package com.example.commandeservice.controller;

import com.example.commandeservice.entity.ShippingInfo;
import com.example.commandeservice.service.ShippingInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/shipping-info")
public class ShippingInfoController {
    @Autowired
    private ShippingInfoService shippingInfoService;

    @PostMapping
    public ResponseEntity<ShippingInfo> createShippingInfo(@Valid @RequestBody ShippingInfo shippingInfo) {
        return ResponseEntity.ok(shippingInfoService.createShippingInfo(shippingInfo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ShippingInfo>> getShippingInfoById(@PathVariable Integer id) {
        return ResponseEntity.ok(shippingInfoService.getShippingInfoById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShippingInfo> updateShippingInfo(@PathVariable Integer id, @Valid @RequestBody ShippingInfo shippingInfo) {
        return ResponseEntity.ok(shippingInfoService.updateShippingInfo(id, shippingInfo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShippingInfo(@PathVariable Integer id) {
        shippingInfoService.deleteShippingInfo(id);
        return ResponseEntity.noContent().build();
    }
}