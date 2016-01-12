package com.engineer.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by Konrad on 24.10.2015.
 */
@Entity
@Table(name="Service")
@PrimaryKeyJoinColumn(name = "shop_object_id")
public class Service extends ShopObject {

    @Column
    private String details;

    public Service(){

    }

    public Service(String name, String description, String barcode, BigDecimal price, Vat vat){
            super(name, description, barcode, price, vat);
        }

        public Service(String name, String description, String barcode, BigDecimal price, Vat vat, String details){
        this(name, description, barcode, price, vat);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
