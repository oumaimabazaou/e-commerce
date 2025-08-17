package com.example.ecommerce.userservice.service;

import com.example.ecommerce.userservice.entity.Client;
import com.example.ecommerce.userservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) { // Changé de String à Long
        return clientRepository.findById(id);
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public void deleteById(Long id) { // Changé de String à Long
        clientRepository.deleteById(id);
    }
}