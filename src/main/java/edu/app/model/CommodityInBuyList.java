package edu.app.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Entity
public class CommodityInBuyList {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private long providerId;
    private long price;
    private ArrayList<String> categories;
    private double rating;
    private long inStock;
    private String imgURL;
    private int quantity;

    @ManyToMany(mappedBy = "purchasedList")
    private final List<User> buyers = new ArrayList<>();

    public CommodityInBuyList(long id, String name, long providerId, long price,
                     ArrayList<String> categories, double rating, long inStock, String imgURL, int quantity) {
        this.id = id;
        this.name = name;
        this.providerId = providerId;
        this.price = price;
        this.categories = categories;
        this.rating = rating;
        this.inStock = inStock;
        this.imgURL = imgURL;
        this.quantity = quantity;
    }
    public CommodityInBuyList(Commodity commodity, int quantity) {
        this.id = commodity.getId();
        this.name = commodity.getName();
        this.providerId = commodity.getProviderId();
        this.price = commodity.getPrice();
        this.categories = commodity.getCategories();
        this.rating = commodity.getRating();
        this.inStock = commodity.getInStock();
        this.imgURL = commodity.getImgURL();
        this.quantity = quantity;
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

    public String toString() {
        return this.name + " -> " + this.id;
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

    public int getQuantity() {
        return quantity;
    }
}
