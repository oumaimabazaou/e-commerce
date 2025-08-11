package com.example.evaluationservice.service;

import com.example.evaluationservice.entity.Evaluation;
import com.example.evaluationservice.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Transactional
    public Evaluation createEvaluation(Evaluation evaluation) {
        if (evaluation.getIdUtilisateur() == null) {
            throw new IllegalArgumentException("idUtilisateur est requis");
        }
        if (evaluation.getIdEntiteEvaluee() == null) {
            throw new IllegalArgumentException("idEntiteEvaluee est requis");
        }
        if (evaluation.getNote() == null || evaluation.getNote() < 1 || evaluation.getNote() > 5) {
            throw new IllegalArgumentException("note doit être entre 1 et 5");
        }
        evaluation.setDateEvaluation(LocalDateTime.now());
        return evaluationRepository.save(evaluation);
    }

    public Optional<Evaluation> getEvaluationById(Integer id) {
        return evaluationRepository.findById(id);
    }

    public List<Evaluation> getEvaluationsByUtilisateur(Integer idUtilisateur) {
        return evaluationRepository.findAll().stream()
                .filter(e -> e.getIdUtilisateur().equals(idUtilisateur))
                .toList();
    }

    public List<Evaluation> getEvaluationsByEntite(Integer idEntiteEvaluee) {
        return evaluationRepository.findAll().stream()
                .filter(e -> e.getIdEntiteEvaluee().equals(idEntiteEvaluee))
                .toList();
    }
}