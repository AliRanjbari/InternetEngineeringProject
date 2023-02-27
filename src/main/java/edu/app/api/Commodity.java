package edu.app.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Commodity {
    long id;
    String name;
    long providerId;
    long price;
    ArrayList<String> categories;
    double rating;
    long inStock;
    Map<String, Double> userRates = new HashMap<String, Double>();


    public Commodity(long id, String name, long providerId, long price, ArrayList<String> categories, double rating, long inStock) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
    }

    public void update (String name, long providerId, long price, ArrayList<String> categories, double rating, long inStock) {
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
    }

    public void rate(String userName , double score){
        if (userRates.containsKey(userName)) {
            double rateToRemove = userRates.remove(userName);
            userRates.put(userName, score);
            double sumOfRates = (this.rating * this.userRates.size() - rateToRemove) + score;
            this.rating = sumOfRates / userRates.size();
        } else {
            double sumOfRates = this.rating * this.userRates.size() + score;
            userRates.put(userName, score);
            this.rating = sumOfRates / userRates.size();
        }
    }


    public long getId() {
        return id;
    }
}
