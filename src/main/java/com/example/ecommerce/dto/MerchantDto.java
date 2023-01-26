package com.example.ecommerce.dto;

import lombok.Data;
import lombok.ToString;
import java.util.Date;

@Data
@ToString
public class MerchantDto {

    private Integer id;
    private String merchantId;
    private String name;
    private String password;
    private String confirmPassword;
    private String email;
    private String address;
    private String phoneNumber;
    private Date createDate;
    private Date modifyDate;
}
