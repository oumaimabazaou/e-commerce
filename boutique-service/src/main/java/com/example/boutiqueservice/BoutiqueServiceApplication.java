package com.example.boutiqueservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.example.boutiqueservice.repository")
@EntityScan(basePackages = "com.example.boutiqueservice.entity")
public class BoutiqueServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoutiqueServiceApplication.class, args);
    }
}