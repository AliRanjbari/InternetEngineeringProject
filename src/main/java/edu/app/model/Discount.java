package edu.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Discount {

    @Id
    private final String discountCode;
    private final long discount;


    public Discount(String discountCode, long discount){
        this.discountCode = discountCode;
        this.discount = discount;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public long getDiscount() {
        return discount;
    }

    public double getRemainingPercentage() {
        return (double) (100 - this.discount) / 100;
    }

}
