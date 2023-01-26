package com.example.ecommerce.dto;

import com.example.ecommerce.entity.Merchant;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class StockDto {
    private String productId;
    private String productName;
    private String image;
    private int quantity;
    private double price;
    private String skuId;
    private Merchant merchant;
    private Date createDate;
    private Date modifyDate;
}
