package com.example.ecommerce.services.impl;

import com.example.ecommerce.dto.MerchantDto;
import com.example.ecommerce.dto.SignInDto;
import com.example.ecommerce.entity.Merchant;
import com.example.ecommerce.repository.MerchantRepository;
import com.example.ecommerce.services.MerchantService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    MerchantRepository merchantRepository;

    @Override
    public Integer signUp(MerchantDto merchantDto) {
        if (Objects.nonNull(merchantRepository.findByEmail(merchantDto.getEmail()))) {
            return 2;
        }

        if (!merchantDto.getPassword().equals(merchantDto.getConfirmPassword()))
            return 3;

        String encryptedPassword = merchantDto.getPassword();
        try {
            encryptedPassword = hashPassword(merchantDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Merchant merchant = new Merchant();
        BeanUtils.copyProperties(merchantDto, merchant);
        merchant.setMerchantId(merchant.getName().toUpperCase().substring(0, 3) + (new SimpleDateFormat("yyyy-MM").format(new Date())));
        merchant.setPassword(encryptedPassword);
        merchantRepository.save(merchant);
        return 1;
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return hash;
    }

    @Override
    public Integer signIn(SignInDto signInDto) {
        Merchant merchant = merchantRepository.findByEmail(signInDto.getEmail());

        if(Objects.isNull(merchantRepository.findByEmail(signInDto.getEmail()))) {
            return 2;
        }

        try {
            if (!merchant.getPassword().equals(hashPassword(signInDto.getPassword())))
                return 3;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    public List<Merchant> findAll() {
        return merchantRepository.findAll();
    }

    @Override
    public Boolean deleteById(String id) {
        if(!merchantRepository.existsById(id))
            return false;
        merchantRepository.deleteById(id);
        return true;
    }

    @Override
    public Merchant findById(String merchantId) {

        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchantId);
        if(optionalMerchant.isPresent()){
            return optionalMerchant.get();
        }
        return null;
    }

    @Override
    public MerchantDto findByEmail(String email) {
        MerchantDto merchantDto = new MerchantDto();
        BeanUtils.copyProperties(merchantRepository.findByEmail(email), merchantDto);
        return merchantDto;
    }

    @Override
    public Merchant findByMerchantId(String merchantId) {
        return merchantRepository.findByMerchantId(merchantId);
    }
}
