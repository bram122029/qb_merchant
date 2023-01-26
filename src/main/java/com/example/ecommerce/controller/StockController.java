package com.example.ecommerce.controller;

import com.example.ecommerce.dto.MerchantDto;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.dto.StockDto;
import com.example.ecommerce.dto.StockUpdateDTO;
import com.example.ecommerce.entity.Merchant;
import com.example.ecommerce.entity.Stock;
import com.example.ecommerce.services.FeignServiceUtil;
import com.example.ecommerce.services.MerchantService;
import com.example.ecommerce.services.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8083/")
@RequestMapping("/stock")
@RestController
public class StockController {

    @Autowired
    MerchantService merchantService;

    @Autowired
    StockService stockService;

    @Autowired
    FeignServiceUtil feignServiceUtil;

    @GetMapping(value = "viewProducts/{merchantId}")
    public List<StockDto> displayAllByMerchantId(@PathVariable("merchantid") String merchantId) {
        List<Stock> stockList = stockService.findByMerchant(merchantId);
        List<StockDto> stockDtos = new ArrayList<>();
        for (Stock stock : stockList) {
            StockDto stockDto = new StockDto();
            BeanUtils.copyProperties(stock, stockDto);
            stockDtos.add(stockDto);
        }
        return stockDtos;
    }

    @GetMapping("/viewByProductId/{productId}")
    public List<StockDto> displayAllByProductId(@PathVariable("productId") String productId){
        List<Stock> stockList = stockService.findByProduct(productId);
        List<StockDto> stockDtos = new ArrayList<>();
        for (Stock stock : stockList) {
            StockDto stockDto = new StockDto();
            BeanUtils.copyProperties(stock, stockDto);
            stockDtos.add(stockDto);
        }
        return stockDtos;
    }

//    @GetMapping("/get")
//    public Map<String,> get() {
//        return feignServiceUtil.getall();
//    }

    @PostMapping("/addProduct/{merchantId}")
    public ResponseEntity<Stock> addProduct(@PathVariable("merchantId") String merchantId,  @RequestBody ProductDto productDto){
        ProductDto productDto1 = feignServiceUtil.addProduct(productDto);
        Merchant merchant = merchantService.findByMerchantId(merchantId);
        productDto1.setCreatedBy(merchant.getMerchantId());

        Stock stock = new Stock();
        //stock.setSkuId("ourSKU");
        stock.setProductId(productDto1.getProductID());
        stock.setProductName(productDto1.getProductName());
        stock.setMerchant(merchant);
        stock.setSkuId(stock.getMerchant().getMerchantId()+stock.getProductId());
        stock.setImage(productDto1.getImageURL());
        StockDto stockDto = new StockDto();
        BeanUtils.copyProperties(stock, stockDto);
        return new ResponseEntity<>(stockService.addStock(stockDto), HttpStatus.OK);
    }

    @PostMapping("/updateStock/{skuId}")
    public ResponseEntity<Stock> updateStock(@PathVariable("skuId") String skuId, @RequestBody StockUpdateDTO stockUpdateDTO){
        Stock stock = stockService.getStock(skuId);
        stock.setPrice(stockUpdateDTO.getPrice());
        stock.setQuantity(stockUpdateDTO.getQuantity());
        StockDto stockDto = new StockDto();
        BeanUtils.copyProperties(stock, stockDto);
        return new ResponseEntity<>(stockService.addStock(stockDto), HttpStatus.OK);
    }

    @PostMapping("/reduceStock/{skuId}/{quantity}")
    public ResponseEntity<Stock> reduceStock(@PathVariable("skuId") String skuId, @PathVariable("quantity") String reduceQuantity){
        Stock stock = stockService.getStock(skuId);
        if(stock.getQuantity() < (int)Double.parseDouble(reduceQuantity))
            return null;
        stock.setQuantity(stock.getQuantity() - ((int)(Double.parseDouble(reduceQuantity))));
        StockDto stockDto = new StockDto();
        BeanUtils.copyProperties(stock, stockDto);
        return new ResponseEntity<>(stockService.addStock(stockDto), HttpStatus.OK);
    }
}
