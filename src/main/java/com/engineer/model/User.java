package com.engineer.model;

import javax.persistence.*;

/**
 * Created by Konrad on 23.09.2015.
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Integer id;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String address;
    @Column
    private String token;

    @ManyToOne
    private City city;
    @Column
    private String postcode;

    @ManyToOne
    private Country country;

    @Column
    private String telephone;




    public User() {
    }
    public User(String email, String password, String name, String surname,  Country country){

        this.password = password;
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.email = email;
    }

    public User(String email, String password, String name, String surname,
                String address, String postcode, City city, Country country,
                String telephone) {
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
        this.email = email;
        this.telephone = telephone;
    }

    public void setId(Integer idUser) {
        this.id = idUser;
    }

    public Integer getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
