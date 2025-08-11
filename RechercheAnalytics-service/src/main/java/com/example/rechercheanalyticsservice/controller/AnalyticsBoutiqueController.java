package com.example.rechercheanalyticsservice.controller;

import com.example.rechercheanalyticsservice.entity.AnalyticsBoutique;
import com.example.rechercheanalyticsservice.service.AnalyticsBoutiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsBoutiqueController {
    @Autowired
    private AnalyticsBoutiqueService analyticsBoutiqueService;

    @PostMapping
    public ResponseEntity<AnalyticsBoutique> createAnalytics(@RequestBody AnalyticsBoutique analytics) {
        return ResponseEntity.ok(analyticsBoutiqueService.createAnalytics(analytics));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnalyticsBoutique> getAnalyticsById(@PathVariable Integer id) {
        return analyticsBoutiqueService.getAnalyticsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/boutique/{idBoutique}")
    public ResponseEntity<List<AnalyticsBoutique>> getAnalyticsByBoutique(@PathVariable Integer idBoutique) {
        return ResponseEntity.ok(analyticsBoutiqueService.getAnalyticsByBoutique(idBoutique));
    }
}