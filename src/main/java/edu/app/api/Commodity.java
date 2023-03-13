package edu.app.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Commodity {
    private long id;
    private String name;
    private long providerId;
    private long price;
    private ArrayList<String> categories;
    private double rating;
    private long inStock;
    private Map<String, Double> userRates = new HashMap<String, Double>();

    private List<Comment> commentList = new ArrayList<Comment>();


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


    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }


    public void rate(String userName , double score){
        if(score > 10)
            throw new RuntimeException("Score is more than 10");
        else if(score < 1)
            throw new RuntimeException("Score os lower than 1");


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

    public boolean doesCategoryExists(String searchCategory) {
        for (String category : this.categories)
            if(searchCategory.equals(category))
                return true;
        return false;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getProviderId() {
        return providerId;
    }

    public long getPrice() {
        return price;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public double getRating() {
        return rating;
    }

    public long getInStock() {
        return inStock;
    }

    public Map<String, Double> getUserRates() {
        return userRates;
    }

    public String toString () {
        return this.name + " -> " + this.id;
    }
}
