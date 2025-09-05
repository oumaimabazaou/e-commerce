package com.example.ecommerce.userservice;

import com.example.ecommerce.userservice.service.UtilisateurService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1000) // Résout l'erreur de "registered order"
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UtilisateurService utilisateurService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        logger.info("En-tête Authorization reçu : {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("Aucun en-tête Authorization valide ou pas de préfixe Bearer");
            chain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        logger.info("Token extrait : {}", jwt);

        try {
            userEmail = jwtTokenProvider.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.utilisateurService.loadUserByUsername(userEmail);

                if (jwtTokenProvider.validateToken(jwt, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("Authentification réussie pour l'utilisateur '{}' avec les rôles : {}", userEmail, userDetails.getAuthorities());
                } else {
                    logger.warn("Token JWT invalide pour l'utilisateur '{}'", userEmail);
                }
            }
        } catch (Exception e) {
            logger.error("Erreur lors du traitement du token JWT : {}", e.getMessage());
        }

        chain.doFilter(request, response);
    }
}