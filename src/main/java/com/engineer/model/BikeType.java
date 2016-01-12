package com.engineer.model;

import javax.persistence.*;

/**
 * Created by Konrad on 08.10.2015.
 */
@Entity
public class BikeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bike_type_id", unique = true, nullable = false)
    private Integer id;

    @Column
    private String name;

    public BikeType() {
    }

    public BikeType(String name) {
        this.name = name;
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
}



