package com.example.ecommerce.apigateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                // <CHANGE> Commentez ou supprimez cette section qui force HTTPS
                /*
                .requiresChannel(channel ->
                    channel.requestMatchers(r -> r.getHeaders().containsKey("X-Forwarded-Proto"))
                           .requiresSecure())
                */
                .csrf().disable()
                .build();
    }
}