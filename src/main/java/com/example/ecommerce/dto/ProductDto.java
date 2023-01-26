package com.example.ecommerce.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Data
@ToString
public class ProductDto {

    private String productID;

    private String productName;
    private String productBrand;
    private Map<String, String> productUSP;
    private String imageURL;
    //Integer rating;
    private CategoryDto productCategory;
    private Boolean deleteStatus;
    private String productDescription;
    private Date createdDate;
    private Date updatedDate;
    private String createdBy;
    private String updatedBy;
}