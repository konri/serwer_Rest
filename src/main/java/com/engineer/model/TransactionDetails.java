package com.engineer.model;


import com.engineer.model.POJO.TransactionDetailsPOJO;

import javax.persistence.*;

/**
 * Created by Konrad on 05.11.2015.
 */

@Entity
@Table(name="TransactionDetails")
public class TransactionDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_details_id", unique = true, nullable = false)
    private Integer transaction_details_id;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "discount")
    private Integer discount;

    public TransactionDetails() {

    }

//    public TransactionDetails(Integer amount){
//        this.amount = amount;
//        this.discount = 0;
//    }

    public TransactionDetails(Integer amount, Integer discount) {
        this.amount = amount;
        this.discount = discount;
    }

    public TransactionDetails(TransactionDetailsPOJO transactionDetailsPojo){
        this.amount = transactionDetailsPojo.getAmount();
        this.discount = transactionDetailsPojo.getDiscount();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getId() {
        return transaction_details_id;
    }
}
