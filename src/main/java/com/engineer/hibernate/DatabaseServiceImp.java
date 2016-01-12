package com.engineer.hibernate;

import com.engineer.encryption.EncryptionUtils;
import com.engineer.enumeration.WeightEnum;
import com.engineer.model.*;
import com.engineer.model.POJO.*;
import com.engineer.model.TransactionDetails;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jboss.logging.Logger;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Konrad on 23.09.2015.
 */
public class DatabaseServiceImp implements DatabaseService {

    /**
     * Check login and password send by user
     * @param email
     * @param password
     * @return true if client send correct login and password.
     */
    @Override
    public User isLoginPasswordCorrect(String email, String password){
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from User where email=:email and password=:password");
            query.setParameter("email", email);
            String encryptedPassword = EncryptionUtils.getInstance().getEncryptedPassword(password);
            query.setParameter("password", encryptedPassword);
            User user = (User) query.uniqueResult();
            return user;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean isEmailExistInDatabase(String email) {
        boolean ret = false;
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from User where email=:email");
            query.setParameter("email", email);
            User user = (User) query.uniqueResult();
            if (user != null)
                ret = true;
        } catch (HibernateException e){
//            ret = isEmailExistInDatabase(email); trying 3 times ?
        } finally {
            session.close();
        }
        return ret;
    }

    @Override
    public User addTokenToUser(String email, String token) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            User user = getUser(session,email);
            user.setToken(token);
            tx.commit();
            return user;
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public City getCity(String name) {
        Session session = SessionUtil.getSession();
        Query query = session.createQuery("from City c where c.name=:name");
        query.setParameter("name", name);
        query.setMaxResults(1);
        City retCity = (City) query.uniqueResult();
        session.close();
        return retCity;
    }

    @Override
    public City addCity(String name) {
        Session session = SessionUtil.getSession();
        Transaction tx = session.getTransaction();
        City city = new City(name);
        session.save(city);
        tx.commit();
        session.close();
        return city;
    }

    public City getCity(Session session, String name) {
        Query query = session.createQuery("from City c where c.name=:name");
        query.setParameter("name", name);
        query.setMaxResults(1);
        City retCity = (City) query.uniqueResult();
        return retCity;
    }

    public City addCity(Session session, String name) {
        Transaction tx = null;
        try {
            tx = session.getTransaction();
            City city = new City(name);
            session.save(city);
            return city;
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        }
        return null;
    }

    @Override
    public City removeCity(City city) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (city != null)
                session.delete(city);
            tx.commit();
            return city;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    private City checkExistCity(String name){
        City city = getCity(name);
        if (city == null){
            city = addCity(name);
        }
        return city;
    }

    private City checkExistCity(Session session, String name){
        City city = getCity(session, name);
        if (city == null){
            city = addCity(session, name);
        }
        return city;
    }

    @Override
    public Country getCountry(String name) {
        Session session = SessionUtil.getSession();
        Query query = session.createQuery("from Country c where c.name=:name");
        query.setParameter("name", name);
        query.setMaxResults(1);
        Country retCountry = (Country) query.uniqueResult();
        return retCountry;
    }

    @Override
    public Country addCountry(String name) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        Country country = new Country(name);
        try {
            session.beginTransaction();
            session.save(country);
            tx.commit();
            return country;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }


    private Country getCountry(Session session, String name) {
        Query query = session.createQuery("from Country c where c.name=:name");
        query.setParameter("name", name);
        query.setMaxResults(1);
        Country retCountry = (Country) query.uniqueResult();
        return retCountry;
    }


    private Country addCountry(Session session, String name) {
        Transaction tx = null;
        Country country = new Country(name);
        try {
            session.getTransaction();
            session.save(country);
            return country;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Country removeCountry(Country country) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (country != null)
                session.delete(country);
            tx.commit();
            return country;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;

    }

    private Country checkExistCountry(String name) {
        Country country = getCountry(name);
        if (country == null) {
            country = addCountry(name);
        }
        return country;
    }

    private Country checkExistCountry(Session session, String name){
        Country country = getCountry(session, name);
        if (country == null) {
            country = addCountry(session, name);
        }
        return country;
    }



    @Override
    public User addUser(String email, String password, String name, String surname,
                        String address, String postcode, String city, String country,
                        String telephone) {
        Country mCountry = checkExistCountry(country);
        City mCity = checkExistCity(city);
        User user = new User(email, EncryptionUtils.getInstance().getEncryptedPassword(password), name, surname,
                address, postcode, mCity, mCountry,
                telephone);
        return addUser(user);
    }

    @Override
    public User addUser(String email, String password, String name, String surname, String country) {
        Country mCountry = checkExistCountry(country);
        User user = new User(email, EncryptionUtils.getInstance().getEncryptedPassword(password), name, surname,
                mCountry);
        return addUser(user);
    }

    @Override
    public User addUser(User user) {
        Session session = SessionUtil.getSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(user);
                tx.commit();
                return user;
            } catch (HibernateException e) {
                e.printStackTrace();
                tx.rollback();
            } finally {
                session.close();
            }
        return null;
    }

    private User getUser(Session session, String email) {
        Query query = session.createQuery("from User where email=:email");
        query.setParameter("email", email);
        query.setMaxResults(1);
        User user = (User) query.uniqueResult();
        return user ;
    }

    @Override
    public User removeUser(User user) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (user != null)
                session.delete(user);
            tx.commit();
            return user;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public User getCustomer(Integer id) {
        return null;
    }

    @Override
    public User getCustomer(String login) {
        return null;
    }

    @Override
    public Boolean checkEmailExsist(String email) {
        boolean ret = false;
        Session session = SessionUtil.getSession();
        try {
            ret = checkEmailExsist(session, email);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return  ret;
    }

    private Boolean checkEmailExsist(Session session, String email) throws HibernateException {
        boolean ret = false;
        Query query = session.createQuery("from User where email=:email");
        query.setParameter("email", email);
        query.setMaxResults(1);
        User user = (User) query.uniqueResult();
        if (user != null) {
            Logger.getLogger(this.getClass()).info("User: " + user.getEmail());
            ret = true;
        } else {
            Logger.getLogger(this.getClass()).info("User: is null" );
        }

        return ret;
    }

    @Override
    public ImageShopObject addImage(String name, byte[] byteImage) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        ImageShopObject image = new ImageShopObject();
        image.setName(name);
        image.setImageData(byteImage);
        try{
            tx = session.beginTransaction();
            session.save(image);
            tx.commit();
            return image;
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public ImageShopObject getImage(int idObject) {
        Session session = SessionUtil.getSession();
        try {
            ImageShopObject image = (ImageShopObject) session.get(ImageShopObject.class, idObject);
            return image;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Brand addBrand(String name) {
        Brand brand = new Brand(name);
        return addBrand(brand);
    }

    @Override
    public Brand addBrand(String name, Set<Manufacturer> manufacturers) {
        Brand brand = new Brand(name, manufacturers);
        return addBrand(brand);
    }

    @Override
    public Brand addBrand(Brand brand) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(brand);
            tx.commit();
            return brand;
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

//    @Override
//    public void addManufactuerToBrand(String brandName, Manufacturer manufacturer) {
//        Brand brand = getBrand(brandName);
//        if (brand == null)
//            brand = addBrand(brandName);
//        Set<Manufacturer> manufacturers = brand.getManufacturers();
//        manufacturers.add(manufacturer);
//        brand.setManufacturers(manufacturers);
//    }

    @Override
    public Brand getBrand(String name) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from Brand where name=:name");
            query.setParameter("name", name);
            query.setMaxResults(1);
            Brand brand = (Brand) query.uniqueResult();
            return brand;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Brand removeBrand(Brand brand) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (brand != null)
                session.delete(brand);
            tx.commit();
            return brand;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    private Brand getBrand(Session session, String name){
        Query query = session.createQuery("from Brand where name=:name");
        query.setParameter("name", name);
        query.setMaxResults(1);
        Brand brand = (Brand) query.uniqueResult();
        return brand;
    }

    private Brand getBrand(Session session, Integer id){
        Query query = session.createQuery("from Brand where id=:id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        Brand brand = (Brand) query.uniqueResult();
        return brand;
    }

    @Override
    public Manufacturer addManufactuer(String name, String address, String city, String postcode, String country, String email, String telephone, Set<Brand> brands) {
        City mCity = checkExistCity(city);
        Country mCountry = checkExistCountry(country);
        Manufacturer manufacturer = new Manufacturer(name, address, mCity, postcode, mCountry, email, telephone, brands);
        return addManufactuer(manufacturer);
    }

    @Override
    public Manufacturer addManufactuer(String name, String address, String city, String postcode, String country, String email, Set<Brand> brands) {
        City mCity = checkExistCity(city);
        Country mCountry = checkExistCountry(country);
        Manufacturer manufacturer = new Manufacturer(name, address, mCity, postcode, mCountry, email, brands);
        return addManufactuer(manufacturer);
    }

    @Override
    public Manufacturer addManufactuer(String name, String address, String city, String postcode, String country, String email, String telephone, String regon, String nip, Set<Brand> brands) {
        City mCity = checkExistCity(city);
        Country mCountry = checkExistCountry(country);
        Manufacturer manufacturer = new Manufacturer(name, address, mCity, postcode, mCountry, email, telephone, regon, nip, brands);
        return addManufactuer(manufacturer);
    }

    @Override
    public Manufacturer addManufactuer(Manufacturer manufacturer) {
        Session session = SessionUtil.getSession();
       Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(manufacturer);
            tx.commit();
            return manufacturer;
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return  null;
    }

    @Override
    public Manufacturer addBrandsToManufactuer(Integer idManufactuer, Set<Brand> brands) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Manufacturer manufacturer = getManufactuer(session, idManufactuer);
            Set<Brand> toSaveBrands = manufacturer.getBrands();
            if (toSaveBrands.addAll(brands)){
                manufacturer.setBrands(toSaveBrands);
            }
            session.save(manufacturer);
            tx.commit();
            return manufacturer;
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }


    @Override
    public Manufacturer getManufactuer(String name) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from Manufacturer where name=:name");
            query.setParameter("name", name);
            query.setMaxResults(1);
            Manufacturer manufacturer = (Manufacturer) query.uniqueResult();
            return manufacturer;
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    private Manufacturer getManufactuer(Session session, String name) {
        Query query = session.createQuery("from Manufacturer where name=:name");
        query.setParameter("name", name);
        query.setMaxResults(1);
        Manufacturer manufacturer = (Manufacturer) query.uniqueResult();
        return manufacturer;
    }

    private Manufacturer getManufactuer(Session session, Integer id) {
        try {
            Query query = session.createQuery("from Manufacturer where manufactuer_id=:id");
            query.setParameter("id", id);
            query.setMaxResults(1);
            Manufacturer manufacturer = (Manufacturer) query.uniqueResult();
            return manufacturer;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Manufacturer getManufactuer(Integer id) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from Manufacturer where manufactuer_id=:id");
            query.setParameter("id", id);
            query.setMaxResults(1);
            Manufacturer manufacturer = (Manufacturer) query.uniqueResult();
            return manufacturer;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Manufacturer removeManufactuer(Manufacturer manufacturer) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (manufacturer != null)
                session.delete(manufacturer);
            tx.commit();
            return manufacturer;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public PartType addPartType(String name) {
        PartType partType = new PartType(name);
        return addPartType(partType);
    }

    private PartType addPartType(PartType partType){
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(partType);
            tx.commit();
            return partType;
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public PartType getPartType(String name) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from PartType where name=:name");
            query.setParameter("name", name);
            query.setMaxResults(1);
            PartType partType = (PartType) query.uniqueResult();
            return partType;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public PartType getPartType(Integer id) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from PartType where id=:id");
            query.setParameter("id", id);
            query.setMaxResults(1);
            PartType partType = (PartType) query.uniqueResult();
            return partType;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public PartType removePartType(PartType partType) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (partType != null)
                session.delete(partType);
            tx.commit();
            return partType;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    private PartType getPartType(Session session, Integer id) {
        try {
            Query query = session.createQuery("from PartType where id=:id");
            query.setParameter("id", id);
            query.setMaxResults(1);
            PartType partType = (PartType) query.uniqueResult();
            return partType;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BikeType addBikeType(String name) {
        BikeType bikeType = new BikeType(name);
        return addBikeType(bikeType);
    }

    private BikeType addBikeType(BikeType bikeType){
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(bikeType);
            tx.commit();
            return bikeType;
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public BikeType getBikeType(String name) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from BikeType where name=:name");
            query.setParameter("name", name);
            query.setMaxResults(1);
            BikeType bikeType = (BikeType) query.uniqueResult();
            return bikeType;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public BikeType getBikeType(Integer id) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from BikeType where id=:id");
            query.setParameter("id", id);
            query.setMaxResults(1);
            BikeType bikeType = (BikeType) query.uniqueResult();
            return bikeType;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public BikeType removeBikeType(BikeType bikeType) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (bikeType != null)
                session.delete(bikeType);
            tx.commit();
            return bikeType;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    private BikeType getBikeType(Session session, Integer id) {
        try {
            Query query = session.createQuery("from BikeType where id=:id");
            query.setParameter("id", id);
            query.setMaxResults(1);
            BikeType bikeType = (BikeType) query.uniqueResult();
            return bikeType;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Bike addBike(String name, String description, String barcode, Integer brand, BigDecimal price, Integer bikeType, Integer vat, BigDecimal weight) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            Brand mBrand = getBrand(session, brand);
            BikeType mBikeType = getBikeType(session, bikeType);
            Vat mVat = getVat(session, vat);
            Bike bike = new Bike(name, description, barcode, price, mVat, mBrand, mBikeType, weight);
            tx = session.beginTransaction();
            session.save(bike);
            tx.commit();
            return bike;
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Bike addBike(String name, String description, String barcode, Integer brand,
                        BigDecimal price, Integer bikeType, Integer vat, BigDecimal weight, Integer amountStock, Integer amountFullStock) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            Brand mBrand = getBrand(session, brand);
            BikeType mBikeType = getBikeType(session, bikeType);
            Vat mVat = getVat(session, vat);
            Bike bike = new Bike(name, description, barcode, price, mVat, mBrand, mBikeType, weight, amountStock, amountFullStock);
            tx = session.beginTransaction();
            session.save(bike);
            tx.commit();
            return bike;
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    private Vat getVat(Session session, Integer vat) {
        try {
            Query query = session.createQuery("from Vat where id=:id");
            query.setParameter("id", vat);
            query.setMaxResults(1);
            Vat mVat = (Vat) query.uniqueResult();
            return mVat;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Bike getBike(Integer id) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from Bike where id=:id");
            query.setParameter("id", id);
            query.setMaxResults(1);
            Bike bike = (Bike) query.uniqueResult();
            return bike;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Bike removeBike(Bike bike) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (bike != null)
                session.delete(bike);
            tx.commit();
            return bike;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    private Bike getBike(Session session, Integer id) {
        try {
            Query query = session.createQuery("from Bike where id=:id");
            query.setParameter("id", id);
            query.setMaxResults(1);
            Bike bike = (Bike) query.uniqueResult();
            return bike;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Part addPart(String name, String description, String barcode, Integer brand, BigDecimal price, Integer vat, Integer partType) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            Brand mBrand = getBrand(session, brand);
            PartType mPartType = getPartType(session, partType);
            Vat mVat = getVat(session, vat);
            Part part = new Part(name, description, barcode, mBrand, price, mVat, mPartType);
            tx = session.beginTransaction();
            session.save(part);
            tx.commit();
            return part;
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Part addPart(String name, String description, String barcode, Integer brand, BigDecimal price, Integer vat,
                        Integer partType, Integer amountStock, Integer amountFullStock) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            Brand mBrand = getBrand(session, brand);
            PartType mPartType = getPartType(session, partType);
            Vat mVat = getVat(session, vat);
            Part part = new Part(name, description, barcode, mBrand, price, mVat, mPartType, amountStock, amountFullStock);
            tx = session.beginTransaction();
            session.save(part);
            tx.commit();
            return part;
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }


    @Override
    public Part getPart(Integer id) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from Part where id=:id");
            query.setParameter("id", id);
            query.setMaxResults(1);
            Part part = (Part) query.uniqueResult();
            return part;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    private Part getPart(Session session, Integer id) {
        try {
            Query query = session.createQuery("from Part where id=:id");
            query.setParameter("id", id);
            query.setMaxResults(1);
            Part bike = (Part) query.uniqueResult();
            return bike;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Part removePart(Part part) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (part != null)
                session.delete(part);
            tx.commit();
            return part;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public ShopObject getShopObject(Integer id) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from ShopObject where id=:id");
            query.setParameter("id", id);
            query.setMaxResults(1);
            ShopObject shopObject = (ShopObject) query.uniqueResult();
            return shopObject;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }


    @Override
    public Service addService(String name, String description, String barcode, BigDecimal price, Integer vat) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            Vat mVat = getVat(session, vat);
            Service service = new Service(name, description, barcode, price, mVat);
            tx = session.beginTransaction();
            session.save(service);
            tx.commit();
            return service;
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Service addService(String name, String description, String barcode, BigDecimal price, Integer vat,
                              String details) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            Vat mVat = getVat(session, vat);
            Service service = new Service(name, description, barcode, price, mVat, details);
            tx = session.beginTransaction();
            session.save(service);
            tx.commit();
            return service;
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Service getService(Integer id) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from Service where id=:id");
            query.setParameter("id", id);
            query.setMaxResults(1);
            Service service = (Service) query.uniqueResult();
            return service;
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Service removeService(Service service) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (service != null)
                session.delete(service);
            tx.commit();
            return service;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Vat getVat(Integer value) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from Vat where value=:value");
            query.setParameter("value",value);
            query.setMaxResults(1);
            Vat vat = (Vat) query.uniqueResult();
            return vat;
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Role addRole(String name, WeightEnum weight) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            Role role = new Role(name, weight);
            tx = session.beginTransaction();
            session.save(role);
            tx.commit();
            return role;
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Role getRole(Integer id) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from Role where id=:id");
            query.setParameter("id",id);
            query.setMaxResults(1);
            Role role = (Role) query.uniqueResult();
            return role;
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Employee addEmployee(String login, String password, String name, String surname,
                                String address, String postcode, String city, String country,
                                String email, String telephone, Role role) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            City mCity = checkExistCity(city);
            Country mCountry = checkExistCountry(country);

            Employee employee = new Employee(login, EncryptionUtils.getInstance().getEncryptedPassword(password),
                                             name, surname, address, postcode, mCity, mCountry,
                                             email, telephone, role);
            tx = session.beginTransaction();
            session.save(employee);
            tx.commit();
            return employee;
        } catch (HibernateException e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Employee getEmployee(Integer id) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from Employee where id=:id");
            query.setParameter("id",id);
            query.setMaxResults(1);
            Employee employee = (Employee) query.uniqueResult();
            return employee;
        } catch (HibernateException e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    private Employee getEmployee(Session session, Integer id) {
        try {
            Query query = session.createQuery("from Employee where id=:id");
            query.setParameter("id",id);
            query.setMaxResults(1);
            Employee employee = (Employee) query.uniqueResult();
            return employee;
        } catch (HibernateException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Employee removeEmployee(Employee employee) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (employee != null)
                session.delete(employee);
            tx.commit();
            return employee;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;

    }

    @Override
    public ShopDetails addShopDetails(String name, String address, String postcode, String city, String country,
                                      String telephone, String email, String regon, String nip) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        City mCity = checkExistCity(city);
        Country mCountry = checkExistCountry(country);
        ShopDetails shopDetails = null;
        if ((shopDetails = getShopDetails(session)) != null) {
            shopDetails.setName(name);
            shopDetails.setAddress(address);
            shopDetails.setPostcode(postcode);
            shopDetails.setCity(mCity);
            shopDetails.setCountry(mCountry);
            shopDetails.setTelephone(telephone);
            shopDetails.setEmail(email);
            shopDetails.setRegon(regon);
            shopDetails.setNip(nip);
        } else {
            shopDetails = new ShopDetails(name, address, postcode, mCity, mCountry, telephone, email, regon, nip);
        }
        try {
            tx = session.beginTransaction();
            session.save(shopDetails);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
        return shopDetails;
    }

    @Override
    public ShopDetails getShopDetails() {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from ShopDetails");
            query.setMaxResults(1);
            ShopDetails shopDetails = (ShopDetails) query.uniqueResult();
            return shopDetails;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public TransactionModule addTransaction(Integer idEmployee, Double netto, Double vat, Double brutto,
                                            Double give, Double change, Map<TransactionDetailsPOJO, ShopObject> orders) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Employee employee = getEmployee(session, idEmployee);
            Map<TransactionDetails, ShopObject> ordersTransaction = new HashMap<>();
            for (Map.Entry<TransactionDetailsPOJO, ShopObject> entry : orders.entrySet()) {
                ordersTransaction.put(addTransactionDetails(session, entry.getKey()), entry.getValue());
            }
            TransactionModule transactionModule = new TransactionModule(employee, netto, vat, brutto, give, change, ordersTransaction);
            session.save(transactionModule);
            tx.commit();
            return transactionModule;
        } catch (HibernateException e) {
            e.printStackTrace();
//            tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    private TransactionDetails addTransactionDetails(Session session, TransactionDetailsPOJO transactionDetails) throws HibernateException{
        TransactionDetails mTransactionDetails = new TransactionDetails(transactionDetails);
        session.save(mTransactionDetails);
        return  mTransactionDetails;
    }

    private  TransactionDetails addTransactionDetails(Session session, Integer amount, Integer discount) throws HibernateException{
        TransactionDetails mTransactionDetails = new TransactionDetails(amount, discount);
        session.save(mTransactionDetails);
        return  mTransactionDetails;
    }

    private TransactionDetails getTransactionDetails(Session session, Integer id) {
        Query query = session.createQuery("from TransactionDetails where id=:id");
        query.setParameter("id", id);
        query.setMaxResults(1);
        TransactionDetails transactionDetails = (TransactionDetails) query.uniqueResult();
        return transactionDetails;
    }

    @Override
    public TransactionModule getTransaction(Integer id) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from TransactionModule where id=:id");
            query.setParameter("id",id);
            query.setMaxResults(1);
            TransactionModule transactionModule = (TransactionModule) query.uniqueResult();
            return transactionModule;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }


    private TransactionModule getTransaction(Session session, Integer id) {
        try {
            Query query = session.createQuery("from TransactionModule where id=:id");
            query.setParameter("id",id);
            query.setMaxResults(1);
            TransactionModule transactionModule = (TransactionModule) query.uniqueResult();
            return transactionModule;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TransactionModule removeTransaction(Integer id) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            TransactionModule transactionModule = getTransaction(session, id);
            if (transactionModule != null){
                session.delete(transactionModule);
                tx.commit();
                return transactionModule;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<ServiceTask> getServiceTasks(String email) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from ServiceTask where user.email=:email");
            query.setParameter("email",email);
            List<ServiceTask> listQuery = query.list();
            return listQuery;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public ServiceTask getServiceTask(Integer id) {
        Session session = SessionUtil.getSession();
        try {
            return getServiceTask(session,id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public ServiceTask addServiceTask(String userEmail, String name, String description) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            ServiceTask serviceTask = new ServiceTask();
            serviceTask.setUser(getUser(session,userEmail));
            serviceTask.setName(name);
            serviceTask.setDescription(description);
            serviceTask.setIsDone(false);
            tx = session.beginTransaction();
            session.save(serviceTask);
            tx.commit();
            return serviceTask;
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    private ServiceTask getServiceTask(Session session, Integer id) {
        Query query = session.createQuery("from ServiceTask where id=:id");
        query.setParameter("id",id);
        query.setMaxResults(1);
        ServiceTask serviceTask = (ServiceTask) query.uniqueResult();
        return serviceTask;
    }

    @Override
    public ServiceTask endServiceTask(Integer id) {
        Session session = SessionUtil.getSession();
        try {
            ServiceTask serviceTask = getServiceTask(session,id);
            serviceTask.setIsDone(true);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public ServiceTask chagneServiceTask(Integer id, String descrption) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            ServiceTask serviceTask = getServiceTask(session, id);

            serviceTask.setIsDone(true);
            serviceTask.setDateFinish(new Date());
            if (descrption != null) {
                serviceTask.setDescription(descrption);
            }
            tx.commit();
            return serviceTask;
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<UserBike> getUserBikes(String email) {
        Session session = SessionUtil.getSession();
        try {
            Query query = session.createQuery("from UserBike where user.email=:email");
            query.setParameter("email",email);
            List<UserBike> listQuery = query.list();
            return listQuery;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public UserBike getUserBike(Integer id) {
        Session session = SessionUtil.getSession();
        try {
            return getUserBike(session, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    private UserBike getUserBike(Session session, Integer id) {
        Query query = session.createQuery("from UserBike where id=:id");
        query.setParameter("id",id);
        query.setMaxResults(1);
        UserBike userBike = (UserBike) query.uniqueResult();
        return userBike;
    }


    @Override
    public UserBike addUserBike(String userEmail, String name, String description, Integer amount, Integer range) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            UserBike userBike = new UserBike();
            userBike.setUser(getUser(session,userEmail));
            userBike.setName(name);
            userBike.setDescription(description);
            userBike.setRange(range);
            userBike.setTotal(amount);
            tx = session.beginTransaction();
            session.save(userBike);
            tx.commit();
            return userBike;
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public UserBike updateUserBike(int id, String userEmail, String name, String description, Integer amount, Integer range) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            UserBike userBike = getUserBike(id);
            userBike.setUser(getUser(session,userEmail));
            userBike.setName(name);
            userBike.setDescription(description);
            userBike.setRange(range);
            userBike.setTotal(amount);
            tx.commit();
            return userBike;
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public UserBike removeBike(Integer id) {
        Session session = SessionUtil.getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            UserBike userBike = getUserBike(session,id);
            session.delete(userBike);
            tx.commit();

            return userBike;
        } catch (HibernateException e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
        return null;
    }

    private ShopDetails getShopDetails(Session session) {
        try {
            Query query = session.createQuery("from ShopDetails");
            query.setMaxResults(1);
            ShopDetails shopDetails = (ShopDetails) query.uniqueResult();
            return shopDetails;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

}
