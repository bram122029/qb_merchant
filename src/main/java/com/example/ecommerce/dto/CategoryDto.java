package com.example.ecommerce.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CategoryDto {
    private String categoryId;
    private String categoryName;
    private Boolean deleteStatus;
}