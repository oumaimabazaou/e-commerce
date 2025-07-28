package com.example.boutiqueservice.service;

import com.example.boutiqueservice.entity.Publicite;
import com.example.boutiqueservice.repository.PubliciteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PubliciteService {
    @Autowired
    private PubliciteRepository publiciteRepository;

    public Publicite createPublicite(Publicite publicite) {
        return publiciteRepository.save(publicite);
    }

    public Optional<Publicite> getPubliciteById(Integer id) {
        return publiciteRepository.findById(id);
    }

    public List<Publicite> getPublicitesByBoutique(Integer idBoutique) {
        return publiciteRepository.findByIdBoutiqueAndActiveTrue(idBoutique);
    }

    public Publicite updatePublicite(Integer id, Publicite publiciteDetails) {
        Publicite publicite = publiciteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publicite not found"));
        publicite.setTitre(publiciteDetails.getTitre());
        publicite.setDescription(publiciteDetails.getDescription());
        publicite.setTypePublicite(publiciteDetails.getTypePublicite());
        publicite.setCible(publiciteDetails.getCible());
        publicite.setImageUrl(publiciteDetails.getImageUrl());
        publicite.setVideoUrl(publiciteDetails.getVideoUrl());
        publicite.setDateDebut(publiciteDetails.getDateDebut());
        publicite.setDateFin(publiciteDetails.getDateFin());
        publicite.setActive(publiciteDetails.getActive());
        publicite.setIdBoutique(publiciteDetails.getIdBoutique());
        publicite.setStatistiques(publiciteDetails.getStatistiques());
        return publiciteRepository.save(publicite);
    }

    public void deletePublicite(Integer id) {
        publiciteRepository.deleteById(id);
    }
}