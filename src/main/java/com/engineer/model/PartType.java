package com.engineer.model;

import javax.persistence.*;

/**
 * Created by Konrad on 13.10.2015.
 */
@Entity
public class PartType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_type_id", unique = true, nullable = false)
    private Integer id;

    @Column
    private String name;

    public PartType(){

    }

    public PartType(String name){
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
