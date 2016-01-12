package com.engineer.model;

import com.engineer.enumeration.WeightEnum;

import javax.persistence.*;

/**
 * Created by Konrad on 25.10.2015.
 */
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column
    private String name;

    @Column
    private Integer weight;

    public Role() {
    }

    public Role(String name, WeightEnum weight) {
        this.name = name;
        this.weight = weight.ordinal();
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
