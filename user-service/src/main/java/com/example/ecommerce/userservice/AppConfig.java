
package com.example.ecommerce.userservice; // Adaptez le package si nécessaire

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced // <-- L'ANNOTATION MAGIQUE QUI CORRIGE TOUT !
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}