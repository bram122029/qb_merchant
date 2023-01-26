package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Merchant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantRepository extends CrudRepository<Merchant, String> {

    List<Merchant> findAll();
    Merchant findByEmail(String email);
    Merchant findByMerchantId(String merchantId);
}
