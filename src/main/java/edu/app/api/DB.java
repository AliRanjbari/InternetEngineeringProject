package edu.app.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DB {
    List<User> users;
    List<Commodity> commodities;
    List<Provider> providers;

    public DB() {
        this.users = new ArrayList<User>();
        this.commodities = new ArrayList<Commodity>();
        this.providers = new ArrayList<Provider>();
    }

    public void addUser(String userName, String password, String email, LocalDate birthDay, String address, long credit) {
        if (findUser(userName) != null){
            User user = findUser(userName);
            user.update(password, email, birthDay, address, credit);
        } else {
            User newUser = new User(userName, password, email, birthDay, address, credit);
            users.add(newUser);
        }
    }

    public void addProvider(long id, String name, LocalDate registryDate) {
        if(findProvider(id) != null) {
            Provider provider = findProvider(id);
            provider.update(name, registryDate);
        } else {
            Provider newProvider = new Provider(id, name, registryDate);
            this.providers.add(newProvider);
        }
    }

    public void addCommodity(long id, String name, long providerId, long price, ArrayList<String> categories, double rating, long inStock) {
        if(findProvider(providerId) == null)
            throw new RuntimeException("Provider does not exists");

        if(findCommodity(id) != null) {
            Commodity commodity = findCommodity(id);
            commodity.update(name, providerId, price, categories, rating, inStock);
        } else {
            Commodity newCommodity = new Commodity(id, name, providerId, price, categories, rating, inStock);
            this.commodities.add(newCommodity);
        }
    }

    public void addCommodity(Commodity inputCommodity){
        if(findProvider(inputCommodity.getProviderId()) == null)
            throw new RuntimeException("Provider does not exists");

        if(findCommodity(inputCommodity.getId()) != null) {
            Commodity newCommodity = findCommodity(inputCommodity.getId());
            newCommodity.update(inputCommodity.getName(), inputCommodity.getProviderId(),
                                inputCommodity.getPrice(), inputCommodity.getCategories(),
                                inputCommodity.getRating(), inputCommodity.getInStock());
        } else {
            Commodity newCommodity = new Commodity(inputCommodity.getId(), inputCommodity.getName(),
                                                    inputCommodity.getProviderId(), inputCommodity.getPrice(),
                                                    inputCommodity.getCategories(), inputCommodity.getRating(),
                                                    inputCommodity.getInStock());
            this.commodities.add(newCommodity);
        }
    }

    List<Commodity> getCommodities() {
        return this.commodities;
    }

    void rateCommodity(String username, long commodityId, double score) {
        if(findUser(username) == null)
            throw new RuntimeException("User not found");
        if(findCommodity(commodityId) == null)
            throw new RuntimeException("Commodity not found");

        Commodity commodity = findCommodity(commodityId);
        commodity.rate(username, score);
    }

    void addToBuyList(String jsonString) {
        System.out.println("adding to buy list");
    }

    void removeFromBuyList(String jsonString) {
        System.out.println("removing from buy list");
    }

    Commodity getCommodityById(long id) {
        Commodity commodity = findCommodity(id);

        return commodity;
    }

    String getCommoditiesByCategory(String jsonString) {
        return "getting commodity by category [json format]";
    }

    String getBuyList(String jsonString) {
        return "getting buy list [json format] ";
    }

    private User findUser(String userName) {
        for (User user : this.users)
            if (userName.equals(user.getUserName()))
                return user;
        return null;
    }

    public Provider findProvider(long id) {
        for (Provider provider : this.providers)
            if(provider.getId() == id)
                return provider;
        return null;
    }

    private Commodity findCommodity(long id) {
        for (Commodity commodity : this.commodities)
            if (commodity.getId() == id)
                return commodity;
        return null;
    }


}

