package com.example.ecommerce.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class StockUpdateDTO {
    private int quantity;
    private double price;
}
