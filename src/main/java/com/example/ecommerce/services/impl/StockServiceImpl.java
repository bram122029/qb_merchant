package com.example.ecommerce.services.impl;

import com.example.ecommerce.dto.StockDto;
import com.example.ecommerce.entity.Stock;
import com.example.ecommerce.repository.StockRepository;
import com.example.ecommerce.services.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockRepository stockRepository;

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock addStock(StockDto stockDto) {
        Stock stock = new Stock();
        BeanUtils.copyProperties(stockDto, stock);
        return stockRepository.save(stock);
    }

    @Override
    public Stock getStock(String skuID) {
        Optional<Stock> optionalStock= stockRepository.findById(skuID);
        if(optionalStock.isPresent()){
            return optionalStock.get();
        }
        return null;
    }

    @Override
    public List<Stock> findByMerchant(String merchantId) {
        return stockRepository.findAllByMerchant_MerchantId(merchantId);
    }

    @Override
    public List<Stock> findByProduct(String productId) {
        return stockRepository.findAllByProductId(productId);
    }
}
