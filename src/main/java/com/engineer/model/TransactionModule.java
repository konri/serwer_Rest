package com.engineer.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * Created by Konrad on 03.11.2015.
 */
@Entity
@Table(name = "Transaction")
public class TransactionModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "date")
    private Date date;

    @Column
    private Double netto;

    @Column
    private Double vat;

    @Column
    private Double brutto;

    @Column
    private Double give;

    @Column(name = "give_change")
    private Double change;

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinTable(
            name = "Transaction_ShopObject",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_object_id")
    )
    @MapKeyJoinColumn(name="transaction_details")
    private Map<TransactionDetails,ShopObject> orders;

    public TransactionModule(){

    }

    public TransactionModule(Employee employee, Double netto, Double vat, Double brutto,
                       Double give, Double change, Map<TransactionDetails, ShopObject> orders){
        this.employee = employee;
        this.netto = netto;
        this.vat = vat;
        this.brutto = brutto;
        this.give = give;
        this.change = change;
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getDate() {
        return date;
    }

    public Double getNetto() {
        return netto;
    }

    public void setNetto(Double netto) {
        this.netto = netto;
    }

    public Double getBrutto() {
        return brutto;
    }

    public void setBrutto(Double brutto) {
        this.brutto = brutto;
    }

    public Double getGive() {
        return give;
    }

    public void setGive(Double give) {
        this.give = give;
    }

    public Double getChange() {
        return change;
    }

    public void setChange(Double change) {
        this.change = change;
    }

    public Map<TransactionDetails, ShopObject> getOrders() {
        return orders;
    }

    public void setOrders(Map<TransactionDetails, ShopObject> orders) {
        this.orders = orders;
    }
}
