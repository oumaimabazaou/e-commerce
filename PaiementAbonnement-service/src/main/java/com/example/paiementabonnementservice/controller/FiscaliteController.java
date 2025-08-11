package com.example.paiementabonnementservice.controller;

import com.example.paiementabonnementservice.entity.Fiscalite;
import com.example.paiementabonnementservice.service.FiscaliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fiscalites")
public class FiscaliteController {
    @Autowired
    private FiscaliteService fiscaliteService;

    @PostMapping
    public ResponseEntity<Fiscalite> createFiscalite(@RequestBody Fiscalite fiscalite) {
        return ResponseEntity.ok(fiscaliteService.createFiscalite(fiscalite));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fiscalite> getFiscaliteById(@PathVariable Integer id) {
        return fiscaliteService.getFiscaliteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paiement/{idPaiement}")
    public ResponseEntity<List<Fiscalite>> getFiscalitesByPaiement(@PathVariable Integer idPaiement) {
        return ResponseEntity.ok(fiscaliteService.getFiscalitesByPaiement(idPaiement));
    }
}