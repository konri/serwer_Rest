package com.engineer.model.POJO;

import com.engineer.model.User;

/**
 * Created by Konrad on 19.11.2015.
 */
public class UserPOJO {
    private String email;
    private String name;
    private String surname;
    private String address;
    private String city;
    private String postcode;
    private String country;
    private String telephone;

    public UserPOJO(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.address = user.getAddress();
        if (user.getCity() != null) {
            this.city = user.getCity().getName();
        }
        this.postcode = user.getPostcode();
        if (user.getCountry() != null) {
            this.country = user.getCountry().getName();
        }
        this.telephone = user.getTelephone();
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    public String getTelephone() {
        return telephone;
    }
}
