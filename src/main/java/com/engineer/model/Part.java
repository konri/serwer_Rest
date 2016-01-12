package com.engineer.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Konrad on 14.10.2015.
 */
@Entity
@Table(name="Part")
@PrimaryKeyJoinColumn(name = "shop_object_id")
public class Part extends ShopObject{

    @ManyToOne
    @JoinColumn(name = "part_type_id")
    private PartType partType;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "amount_stock")
    private Integer stockAmount;

    @Column(name = "amount_stock_seson")
    private Integer fullStockAmount;

    public Part(){

    }

    public Part(String name, String description, String barcode, Brand brand, BigDecimal price, Vat vat,
                PartType partType) {
        super(name, description, barcode, price, vat);
        this.brand = brand;
        this.partType = partType;
    }

    public Part(String name, String description, String barcode, Brand brand, BigDecimal price, Vat vat,
                PartType partType, Integer stockAmount, Integer fullStockAmount) {
        this(name, description, barcode, brand, price, vat, partType);
        this.stockAmount = stockAmount;
        this.fullStockAmount = fullStockAmount;
    }

    public PartType getPartType() {
        return partType;
    }

    public void setPartType(PartType partType) {
        this.partType = partType;
    }

    public Integer getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(Integer stockAmount) {
        this.stockAmount = stockAmount;
    }

    public Integer getFullStockAmount() {
        return fullStockAmount;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
