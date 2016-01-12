package com.engineer.model;

import javax.persistence.*;

/**
 * Created by Konrad on 23.09.2015.
 */
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    public City() {
    }

    public City(String name) {
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

    public void setId(Integer idCity) {
        this.id = idCity;
    }
}
