package com.engineer.model;

import javax.persistence.*;

/**
 * Created by Konrad on 24.10.2015.
 */
@Entity
@Table(name = "Vat")
public class Vat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vat_id", unique = true, nullable = false)
    private Integer id;

    @Column
    private Integer value;

    public Vat() {
    }

    public Vat(Integer value) {
        this.value = value;
    }


    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }
}
