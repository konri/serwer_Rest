package com.engineer.hibernate;

import com.engineer.enumeration.WeightEnum;
import com.engineer.model.*;
import com.engineer.model.POJO.TransactionDetailsPOJO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Konrad on 23.09.2015.
 */
public interface DatabaseService {
    User isLoginPasswordCorrect(String email, String password);

    boolean isEmailExistInDatabase(String email);
    User addTokenToUser(String email, String token);
    City addCity(String name);
    City getCity(String name);
    City removeCity(City city);

    Country addCountry(String name);
    Country getCountry(String name);

    Country removeCountry(Country country);

    User addUser(String email, String password, String name, String surname,
                 String address, String postcode, String city, String country,
                 String telephone);
    User addUser(String email, String password, String name, String surname, String country);
    User addUser(User user); //todo ?? na pewno chce miesc addUser z instancja?
    User removeUser(User user);
    User getCustomer(Integer id);
    User getCustomer(String login);
    Boolean checkEmailExsist(String email);

    ImageShopObject addImage(String name, byte [] byteImage);
    ImageShopObject getImage(int idObject);


    Brand addBrand(String name);
    Brand addBrand(String name, Set<Manufacturer> manufacturers);
    Brand addBrand(Brand brand);
//    void addManufactuerToBrand(String brandName, Manufacturer manufacturer);
    Brand getBrand(String name);
    Brand removeBrand(Brand brand);

    Manufacturer addManufactuer(String name, String address,
                               String city, String postcode, String country,
                       String email, String telephone, Set<Brand> brands);
    Manufacturer addManufactuer(String name, String address,
                               String city, String postcode, String country,
                              String email, Set<Brand> brands);
    Manufacturer addManufactuer(String name, String address,
                               String city, String postcode, String country,
                               String email, String telephone,
                               String regon, String nip, Set<Brand> brands);
    Manufacturer addManufactuer(Manufacturer manufacturer);
    Manufacturer addBrandsToManufactuer(Integer idManufactuer, Set<Brand> brands);
    Manufacturer getManufactuer(String name);
    Manufacturer getManufactuer(Integer id);
    Manufacturer removeManufactuer(Manufacturer manufacturer);

    PartType addPartType(String name);
    PartType getPartType(String name);
    PartType getPartType(Integer id);
    PartType removePartType(PartType partType);

    BikeType addBikeType(String name);
    BikeType getBikeType(String name);
    BikeType getBikeType(Integer id);
    BikeType removeBikeType(BikeType bikeType);

    Bike addBike(String name, String description, String barcode, Integer brand,
                 BigDecimal price, Integer bikeType, Integer vat, BigDecimal weight);
    Bike addBike(String name, String description, String barcode, Integer brand,
                 BigDecimal price, Integer bikeType, Integer vat, BigDecimal weight, Integer amountStock, Integer amountFullStock);
    Bike getBike(Integer id);
    Bike removeBike(Bike bike);

    Part addPart(String name, String description, String barcode, Integer brand, BigDecimal price, Integer vat,
                 Integer partType);
    Part addPart(String name, String description, String barcode, Integer brand, BigDecimal price, Integer vat,
                 Integer partType, Integer amountStock, Integer amountFullStock);
    Part getPart(Integer id);
    Part removePart(Part part);

    ShopObject getShopObject(Integer id);

    Service addService(String name, String description, String barcode, BigDecimal price, Integer vat);
    Service addService(String name, String description, String barcode, BigDecimal price, Integer vat, String details);
    Service getService(Integer id);
    Service removeService(Service service);

    Vat getVat(Integer value);

    Role addRole(String name, WeightEnum weight);
    Role getRole(Integer id);

    Employee addEmployee(String login, String password, String name, String surname,
                         String address, String postcode, String city, String country,
                         String email, String telephone, Role role);
    Employee getEmployee(Integer id);
    Employee removeEmployee(Employee employee);

    ShopDetails addShopDetails(String name, String address, String postcode, String city, String country,
                               String telephone, String email, String regon, String nip);
    ShopDetails getShopDetails();


    TransactionModule addTransaction(Integer idEmployee, Double netto, Double vat, Double brutto,
                               Double give, Double change, Map<TransactionDetailsPOJO, ShopObject> orders);
    TransactionModule getTransaction(Integer id);
    TransactionModule removeTransaction(Integer id);

    List<ServiceTask> getServiceTasks(String email);
    ServiceTask getServiceTask(Integer id);
    ServiceTask addServiceTask(String userEmail, String name, String description);
    ServiceTask endServiceTask(Integer id);
    ServiceTask chagneServiceTask(Integer id, String descrption);

    List<UserBike> getUserBikes(String email);
    UserBike getUserBike(Integer id);
    UserBike addUserBike(String userEmail, String name, String description, Integer amount, Integer range);
    UserBike updateUserBike(int id, String userEmail, String name, String description, Integer amount, Integer range);
    UserBike removeBike(Integer id);

}
