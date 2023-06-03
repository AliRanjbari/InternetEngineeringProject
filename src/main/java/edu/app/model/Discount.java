package edu.app.model;

import edu.app.model.User.User;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Discount {

    @Id
    private final String discountCode;
    private final long discount;

    @OneToMany(mappedBy = "currentDiscount")
    private final List<User> currentUsers = new ArrayList<>();

    @ManyToMany(mappedBy = "usedDiscount")
    private final List<User> pastUsers = new ArrayList<>();

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
