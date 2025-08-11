package com.example.commandeservice.service;

import com.example.commandeservice.entity.Litige;
import com.example.commandeservice.repository.LitigeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LitigeServiceImpl implements LitigeService {
    @Autowired
    private LitigeRepository litigeRepository;

    @Override
    @Transactional
    public Litige createLitige(Litige litige) {
        litige.setDateCreation(LocalDateTime.now());
        litige.setStatut("Ouvert");
        return litigeRepository.save(litige);
    }

    @Override
    public Optional<Litige> getLitigeById(Integer id) {
        return litigeRepository.findById(id);
    }

    @Override
    public List<Litige> getLitigesByCommande(Integer commandeId) {
        return litigeRepository.findByCommande_IdCommande(commandeId);
    }

    @Override
    @Transactional
    public Litige updateLitige(Integer id, Litige litige) {
        Litige existing = litigeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Litige non trouvé"));
        existing.setDescription(litige.getDescription());
        return litigeRepository.save(existing);
    }

    @Override
    @Transactional
    public void resolveLitige(Integer id, String resolution) {
        Litige litige = litigeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Litige non trouvé"));
        litige.setStatut("Résolu");
        litige.setResolution(resolution);
        litige.setDateResolution(LocalDateTime.now());
        litigeRepository.save(litige);
    }

    @Override
    public void deleteLitige(Integer id) {
        litigeRepository.deleteById(id);
    }
}