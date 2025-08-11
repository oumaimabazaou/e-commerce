package com.example.evaluationservice.controller;

import com.example.evaluationservice.entity.Evaluation;
import com.example.evaluationservice.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {
    @Autowired
    private EvaluationService evaluationService;

    @PostMapping
    public ResponseEntity<Evaluation> createEvaluation(@RequestBody Evaluation evaluation) {
        return ResponseEntity.ok(evaluationService.createEvaluation(evaluation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evaluation> getEvaluationById(@PathVariable Integer id) {
        return evaluationService.getEvaluationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/utilisateur/{idUtilisateur}")
    public ResponseEntity<List<Evaluation>> getEvaluationsByUtilisateur(@PathVariable Integer idUtilisateur) {
        return ResponseEntity.ok(evaluationService.getEvaluationsByUtilisateur(idUtilisateur));
    }

    @GetMapping("/entite/{idEntiteEvaluee}")
    public ResponseEntity<List<Evaluation>> getEvaluationsByEntite(@PathVariable Integer idEntiteEvaluee) {
        return ResponseEntity.ok(evaluationService.getEvaluationsByEntite(idEntiteEvaluee));
    }
}