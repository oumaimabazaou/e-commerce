package com.example.commandeservice.service;

import com.example.commandeservice.entity.ShippingInfo;
import com.example.commandeservice.repository.ShippingInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ShippingInfoServiceImpl implements ShippingInfoService {
    @Autowired
    private ShippingInfoRepository shippingInfoRepository;

    @Override
    @Transactional
    public ShippingInfo createShippingInfo(ShippingInfo shippingInfo) {
        return shippingInfoRepository.save(shippingInfo);
    }

    @Override
    public Optional<ShippingInfo> getShippingInfoById(Integer id) {
        return shippingInfoRepository.findById(id);
    }

    @Override
    @Transactional
    public ShippingInfo updateShippingInfo(Integer id, ShippingInfo shippingInfo) {
        ShippingInfo existing = shippingInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipping Info non trouvé"));
        existing.setAdresse(shippingInfo.getAdresse());
        existing.setVille(shippingInfo.getVille());
        return shippingInfoRepository.save(existing);
    }

    @Override
    public void deleteShippingInfo(Integer id) {
        shippingInfoRepository.deleteById(id);
    }
}