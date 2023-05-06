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
    private String imgURL;
    private Map<String, Double> userRates = new HashMap<>();

    private List<Comment> commentList = new ArrayList<Comment>();


    public Commodity(long id, String name, long providerId, long price,
                     ArrayList<String> categories, double rating, long inStock, String imgURL) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
        this.imgURL = imgURL;

    }
    public Commodity(Commodity commodity) {
        this.id = commodity.getId();
        this.name = commodity.getName();
        this.providerId = commodity.getProviderId();
        this.price = commodity.getPrice();
        this.categories = commodity.getCategories();
        this.rating = commodity.getRating();
        this.inStock = commodity.getInStock();
        this.imgURL = commodity.getImgURL();
    }

    public void update(String name, long providerId, long price,
                       ArrayList<String> categories, double rating, long inStock, String imgURL) {
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
        this.imgURL = imgURL;
    }


    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }


    public void rate(String userName, double score) {
        if (score > 10)
            throw new RuntimeException("Score is more than 10");
        else if (score < 1)
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
            if (searchCategory.equals(category))
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

    public String toString() {
        return this.name + " -> " + this.id;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public String getImgURL() {
        return this.imgURL;
    }

    public int hasSameCategories(Commodity commodity) {
        if (this.categories.size() != commodity.getCategories().size())
            return 0;
        for (String category : this.categories)
            if (!commodity.getCategories().contains(category))
                return 0;
        return 1;
    }
    public void decreaseInStock(){
        this.inStock = this.getInStock() - 1;
    }
    public void increaseInStock(){
        this.inStock = this.getInStock() + 1;
    }
}
