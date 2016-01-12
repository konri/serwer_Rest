package com.engineer.model;


import javax.persistence.*;

/**
 * Created by Konrad on 30.10.2015.
 */
@Entity
@Table(name = "ShopDetails")
public class ShopDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;
    @Column
    private String address;
    @Column
    private String postcode;

    @ManyToOne
    @JoinColumn(name = "city")
    private City city;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;
    @Column
    private String telephone; //todo maybe we can make OneToMany with table phones, beacuse the company can have a multiple telephones like emails.
    @Column
    private String email;
    @Column
    private String regon;
    @Column
    private String nip;

    public ShopDetails(){

    }
    public ShopDetails(String name, String address, String postcode, City city, Country country,
                       String telephone, String email, String regon, String nip){
        this.name = name;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.country = country;
        this.telephone = telephone;
        this.email = email;
        this.regon = regon;
        this.nip = nip;

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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
