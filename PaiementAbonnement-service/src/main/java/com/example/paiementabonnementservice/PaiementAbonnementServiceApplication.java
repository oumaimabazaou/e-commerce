package com.example.paiementabonnementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaiementAbonnementServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaiementAbonnementServiceApplication.class, args);
    }
}