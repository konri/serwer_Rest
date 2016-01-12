package com.engineer.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Konrad on 11.10.2015.
 */
@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "brands")
    private Set<Manufacturer> manufacturers = new HashSet<>(0);

    public Brand() {

    }

    public Brand(String name) {
        this.name = name;

    }

    public Brand(String name, Set<Manufacturer> manufacturers) {
        this(name);
        this.manufacturers = manufacturers;
    }

    public Set<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(Set<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
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
