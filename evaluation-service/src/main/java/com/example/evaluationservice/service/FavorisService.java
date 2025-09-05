package com.example.evaluationservice.service;

import com.example.evaluationservice.entity.Favoris;
import com.example.evaluationservice.repository.FavorisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FavorisService {
    @Autowired
    private FavorisRepository favorisRepository;

    @Transactional
    public Favoris createFavori(Favoris favoris) {
        if (favoris.getIdUtilisateur() == null) {
            throw new IllegalArgumentException("idUtilisateur est requis");
        }
        if (favoris.getIdEntiteFavoree() == null) {
            throw new IllegalArgumentException("idEntiteFavoree est requis");
        }
        favoris.setDateAjout(LocalDateTime.now());
        return favorisRepository.save(favoris);
    }

    public Optional<Favoris> getFavoriById(Integer id) {
        return favorisRepository.findById(id);
    }

    public List<Favoris> getFavorisByEntite(Integer idEntiteFavoree) {
        return favorisRepository.findAll().stream()
                .filter(f -> f.getIdEntiteFavoree().equals(idEntiteFavoree))
                .toList();
    }

    public List<Favoris> getAllFavoris() {
        return favorisRepository.findAll();
    }
    public List<Favoris> getFavorisByUtilisateur(Long idUtilisateur) {
        return favorisRepository.findAll().stream()
                .filter(f -> f.getIdUtilisateur().equals(idUtilisateur))
                .toList();
    }

}