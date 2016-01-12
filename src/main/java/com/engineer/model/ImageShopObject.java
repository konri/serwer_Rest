package com.engineer.model;

import javax.persistence.*;

/**
 * Created by Konrad on 08.10.2015.
 */
@Entity
@Table(name = "ShopImages")
public class ImageShopObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name ="name", nullable = false)
    private String name;

    @Lob
    @Column(name="image", nullable=false, columnDefinition="blob")
    private byte[] imageData;

    public ImageShopObject() {
    }

    public ImageShopObject(String name, byte[] image) {
        this.name = name;
        this.imageData = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}



