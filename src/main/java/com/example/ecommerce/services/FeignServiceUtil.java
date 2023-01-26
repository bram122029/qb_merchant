package com.example.ecommerce.services;

import com.example.ecommerce.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "merchantFeign", url =  "http://10.20.2.188:8089/product")
public interface FeignServiceUtil {

    @PostMapping("/add")
    ProductDto addProduct(@RequestBody ProductDto productDto);
}
