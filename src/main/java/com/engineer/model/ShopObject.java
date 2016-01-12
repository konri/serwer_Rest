package com.engineer.model;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Konrad on 11.10.2015.
 */
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class ShopObject {
    @Id
    @GeneratedValue
    @Column(name = "shop_object_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "barcode")
    private String barcode;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "vat_id")
    private Vat vat;



    public ShopObject(){

    }

    public ShopObject(String name, String description, String barcode, BigDecimal price, Vat vat){
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.price = price;
        this.vat = vat;
    }


    public Integer getId() {
        return id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vat getVat() {
        return vat;
    }

    public void setVat(Vat vat) {
        this.vat = vat;
    }
}
