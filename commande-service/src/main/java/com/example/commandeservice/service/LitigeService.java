package com.example.commandeservice.service;

import com.example.commandeservice.entity.Litige;
import java.util.List;
import java.util.Optional;

public interface LitigeService {
    Litige createLitige(Litige litige);
    Optional<Litige> getLitigeById(Integer id);
    List<Litige> getLitigesByCommande(Integer commandeId);
    Litige updateLitige(Integer id, Litige litige);
    void resolveLitige(Integer id, String resolution);
    void deleteLitige(Integer id);
}