package edu.app.api;

public class Discount {
    private String discountCode;
    private long discount;


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
