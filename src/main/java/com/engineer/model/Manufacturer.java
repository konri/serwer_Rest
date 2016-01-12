package com.engineer.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Konrad on 11.10.2015.
 */

@Entity
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer manufactuer_id;

    @Column
    private String name;

    @Column
    private String address;

    @ManyToOne
    private City city;

    @Column
    private String postcode;

    @ManyToOne
    private Country country;

    @Column
    private String email;

    @Column
    private String telephone;

    @Column
    private String regon;

    @Column
    private String nip;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "brand_manufacturer",
            joinColumns = @JoinColumn(name = "manufactuer_id"),
            inverseJoinColumns = @JoinColumn(name = "brand_id")
    )
    public Set<Brand> brands = new HashSet<>(0);


    public Manufacturer() {
    }

    public Manufacturer(String name, String address,
                        City city, String postcode, Country country,
                        String email, String telephone, Set<Brand> brands) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
        this.email = email;
        this.telephone = telephone;
        this.brands = brands;
    }

    public Manufacturer(String name, String address,
                        City city, String postcode, Country country,
                        String email, Set<Brand> brands) {
        this(name, address, city, postcode, country, email, "", brands);
    }

    public Manufacturer(String name, String address,
                        City city, String postcode, Country country,
                        String email, String telephone,
                        String regon, String nip, Set<Brand> brands) {
        this(name, address, city, postcode, country, email, telephone, brands);
        this.regon = regon;
        this.nip = nip;
        this.brands = brands;
    }


    public Integer getId() {
        return manufactuer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public Set<Brand> getBrands() {
        return brands;
    }

    public void setBrands(Set<Brand> brands) {
        this.brands = brands;
    }
}

