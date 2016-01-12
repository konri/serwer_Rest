package com.engineer.model;

import javax.persistence.*;

/**
 * Created by Konrad on 23.09.2015.
 */
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String address;

    @ManyToOne
    @JoinColumn(name = "city")
    private City city;

    @Column
    private String postcode;

    @ManyToOne
    @JoinColumn(name = "country")
    private Country country;

    @Column
    private String email;

    @Column
    private String telephone;


    public Employee() {
    }

    public Employee(String login, String password, String name, String surname,
                    String address, String postcode, City city, Country country,
                    String email, String telephone, Role role) {
        this.role = role;
        this.login = login;
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


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

//Todo: delete user from database
//    @DELETE
//    public void removeFromDatabase() {
//        SchoolStudents.schoolServices.removeStudent(this);
//    }


}
