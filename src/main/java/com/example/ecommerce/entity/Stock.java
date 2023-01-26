package com.example.ecommerce.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@ToString
@Table(name = "stock")
@Entity
public class Stock {

    private String productId;
    private String productName;
    private String image;
    private int quantity;
    private double price;
    @Id
    private String skuId;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "merchant_id", referencedColumnName = "merchantId")
    private Merchant merchant;
}
