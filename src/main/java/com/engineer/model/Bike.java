package com.engineer.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Konrad on 14.10.2015.
 */
@Entity
@Table(name="Bike")
@PrimaryKeyJoinColumn(name = "shop_object_id")
public class Bike extends ShopObject {

    @ManyToOne
    @JoinColumn(name = "bike_type_id")
    private BikeType bike_type;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "weight", precision = 2, scale = 3)
    private BigDecimal weight;

    @Column(name = "amount_stock")
    private Integer stockAmount;

    @Column(name = "amount_stock_seson")
    private Integer fullStockAmount;

    public Bike(){

    }

    public Bike(String name, String description, String barcode, BigDecimal price, Vat vat, Brand brand, BikeType bikeType, BigDecimal weight) {
        super(name, description, barcode, price, vat);
        this.brand = brand;
        this.weight = weight;
        this.bike_type = bikeType;
    }

    public Bike(String name, String description, String barcode, BigDecimal price, Vat vat, Brand brand, BikeType bikeType, BigDecimal weight, Integer stockAmount, Integer fullStockAmount) {
        this(name, description, barcode, price, vat, brand, bikeType, weight);
        this.stockAmount = stockAmount;
        this.fullStockAmount = fullStockAmount;
    }


        public BikeType getBike_type() {
        return bike_type;
    }

    public void setBike_type(BikeType bikeType) {
        this.bike_type = bikeType;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
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

    public void setFullStockAmount(Integer fullStockAmount) {
        this.fullStockAmount = fullStockAmount;
    }
}
