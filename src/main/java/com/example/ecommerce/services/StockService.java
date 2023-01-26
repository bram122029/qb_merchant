package com.example.ecommerce.services;

import com.example.ecommerce.dto.StockDto;

import com.example.ecommerce.entity.Stock;
import java.util.List;

public interface StockService {

    List<Stock> findAll();
    Stock addStock(StockDto stockDto);
    Stock getStock(String skuID);
    List<Stock> findByMerchant(String merchantId);
    List<Stock> findByProduct(String productId);
}