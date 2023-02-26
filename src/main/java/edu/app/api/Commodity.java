package edu.app.api;

import java.util.ArrayList;
import java.util.List;

public class Commodity {
    long id;
    String name;
    long providerId;
    long price;
    String[] categories;
    double rating;
    long inStock;
    String[] userIds;


    public Commodity(long id, String name, long providerId, long price, String[] categories, double rating, long inStock) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
    }

}
