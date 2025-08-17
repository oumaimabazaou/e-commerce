package com.example.commandeservice.service;

import com.example.commandeservice.entity.ShippingInfo;
import java.util.Optional;

public interface ShippingInfoService {
    ShippingInfo createShippingInfo(ShippingInfo shippingInfo);
    Optional<ShippingInfo> getShippingInfoById(Integer id);
    ShippingInfo updateShippingInfo(Integer id, ShippingInfo shippingInfo);
    void deleteShippingInfo(Integer id);
}