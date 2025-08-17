package com.example.boutiqueservice.service;

import com.example.boutiqueservice.entity.Publicite;
import com.example.boutiqueservice.repository.PubliciteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PubliciteService {
    private static final Logger logger = LoggerFactory.getLogger(PubliciteService.class);

    @Autowired
    private PubliciteRepository publiciteRepository;

    public Publicite createPublicite(Publicite publicite) {
        // Validation des champs obligatoires
        if (publicite == null || publicite.getIdBoutique() == null || publicite.getTitre() == null || publicite.getTitre().trim().isEmpty()) {
            throw new IllegalArgumentException("idBoutique et titre sont requis et ne peuvent pas être vides.");
        }
        // Vérification des dates
        if (publicite.getDateDebut() == null || publicite.getDateFin() == null) {
            throw new IllegalArgumentException("dateDebut et dateFin sont requis.");
        }
        if (publicite.getDateDebut().isAfter(publicite.getDateFin())) {
            throw new IllegalArgumentException("dateDebut doit être avant dateFin.");
        }
        // Vérification supplémentaire des dates (valeurs réalistes)
        if (publicite.getDateDebut().isBefore(LocalDateTime.now().minusYears(1)) ||
                publicite.getDateFin().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Les dates doivent être dans le futur ou actuelles.");
        }
        // Vérification que idBoutique est positif
        if (publicite.getIdBoutique() <= 0) {
            throw new IllegalArgumentException("idBoutique doit être un entier positif.");
        }

        // Initialisation des valeurs par défaut
        if (publicite.getDateCreation() == null) publicite.setDateCreation(LocalDateTime.now());
        if (publicite.getActive() == null) publicite.setActive(true);

        logger.info("Tentative de création d'une publicité pour titre: {}", publicite.getTitre());
        try {
            return publiciteRepository.save(publicite);
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur de contrainte de données : {}", e.getMessage(), e);
            throw new RuntimeException("Erreur de contrainte de données : Vérifiez si id_boutique existe dans la table boutique ou si la table publicite est correctement configurée. Détail : " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Erreur lors de la création : {}", e.getMessage(), e);
            throw new RuntimeException("Erreur inattendue lors de la création : Vérifiez la configuration de la base de données. Détail : " + e.getMessage(), e);
        }
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
        if (publiciteDetails.getDateDebut() != null && publiciteDetails.getDateFin() != null &&
                !publiciteDetails.getDateDebut().isAfter(publiciteDetails.getDateFin())) {
            publicite.setDateDebut(publiciteDetails.getDateDebut());
            publicite.setDateFin(publiciteDetails.getDateFin());
        }
        if (publiciteDetails.getActive() != null) publicite.setActive(publiciteDetails.getActive());
        if (publiciteDetails.getIdBoutique() != null && publiciteDetails.getIdBoutique() > 0) {
            publicite.setIdBoutique(publiciteDetails.getIdBoutique());
        }
        if (publiciteDetails.getStatistiques() != null) publicite.setStatistiques(publiciteDetails.getStatistiques());
        return publiciteRepository.save(publicite);
    }

    public void deletePublicite(Integer id) {
        if (!publiciteRepository.existsById(id)) {
            throw new RuntimeException("Publicite not found");
        }
        publiciteRepository.deleteById(id);
    }
}