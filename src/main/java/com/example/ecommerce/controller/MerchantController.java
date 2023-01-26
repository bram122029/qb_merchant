package com.example.ecommerce.controller;

import com.example.ecommerce.dto.DisplayMerchantDto;
import com.example.ecommerce.dto.MerchantDto;
import com.example.ecommerce.dto.MerchantEmailDto;
import com.example.ecommerce.dto.SignInDto;
import com.example.ecommerce.entity.Merchant;
import com.example.ecommerce.services.MerchantService;
import com.example.ecommerce.services.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping("/merchant")
@RestController
public class MerchantController {
    @Autowired
    MerchantService merchantService;

    @Autowired
    StockService stockService;

    @PostMapping(value = "signUp")
    public ResponseEntity<Integer> signUp(@RequestBody MerchantDto merchantDto) {
        return new ResponseEntity<>(merchantService.signUp(merchantDto), HttpStatus.OK);
    }

    @PostMapping(value = "signIn")
    public ResponseEntity<Integer> signIn(@RequestBody SignInDto signInDto) {
        int response = merchantService.signIn(signInDto);
        if(response == 1)
            return new ResponseEntity<>(1, HttpStatus.OK); //Success
        else if(response == 2)
            return new ResponseEntity<>(2, HttpStatus.OK); //Incorrect Email
        else if(response == 3)
            return new ResponseEntity<>(3, HttpStatus.OK); //Incorrect Password

        return null;
    }

    @GetMapping(value = "displayAll")
    public List<DisplayMerchantDto> displayAll() {
        List<Merchant> merchantList = merchantService.findAll();
        List<DisplayMerchantDto> displayMerchantDtos = new ArrayList<>();
        for (Merchant merchant : merchantList) {
            DisplayMerchantDto displayMerchantDto = new DisplayMerchantDto();
            BeanUtils.copyProperties(merchant, displayMerchantDto);
            displayMerchantDtos.add(displayMerchantDto);
        }
        return displayMerchantDtos;
    }

    @DeleteMapping(value = "/deleteById/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id) {
         return  new ResponseEntity<>(merchantService.deleteById(id), HttpStatus.OK);
    }

    @GetMapping("/getByEmail/{email}")
    public ResponseEntity<MerchantDto> getByEmail(@PathVariable("email") String email){
        return new ResponseEntity<>(merchantService.findByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/getMerchant/{email}")
    public MerchantDto getMerchant(@PathVariable("email") String email){
        return merchantService.findByEmail(email);
    }
}
