package com.engineer.model.POJO;

import com.engineer.model.ShopObject;

/**
 * Created by Konrad on 06.11.2015.
 */
public class TransactionDetailsPOJO {
    private Integer amount;
    private Integer discount;

    public TransactionDetailsPOJO(Integer amount){
        this.amount = amount;
        this.discount = 0;
    }

    public TransactionDetailsPOJO(Integer amount, Integer discount){
        this.amount = amount;
        this.discount = discount;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getDiscount() {
        return discount;
    }
}
