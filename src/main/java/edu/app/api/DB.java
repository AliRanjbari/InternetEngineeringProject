package edu.app.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DB {
    List<User> users;
    List<Commodity> commodities;
    List<Provider> providers;

    DB() {
        this.users = new ArrayList<User>();
        this.commodities = new ArrayList<Commodity>();
        this.providers = new ArrayList<Provider>();
    }

    void addUser(String userName, String password, String email, LocalDate birthDay, String address, long credit) {
        if (findUser(userName) != null){
            User user = findUser(userName);
            user.update(password, email, birthDay, address, credit);
        } else {
            User newUser = new User(userName, password, email, birthDay, address, credit);
            users.add(newUser);
        }
    }

    void addProvider(long id, String name, LocalDate registryDate) {
        if(findProvider(id) != null) {
            Provider provider = findProvider(id);
            provider.update(name, registryDate);
        } else {
            Provider newProvider = new Provider(id, name, registryDate);
            this.providers.add(newProvider);
        }
    }

    void addCommodity(String jsonString) {
        System.out.println("adding commodity");
    }

    String getCommodityList(String jsonString){
        return "Commodity list";
    }

    void rateCommodity(String jsonString) {
        System.out.println("rating commodity");
    }

    void addToBuyList(String jsonString) {
        System.out.println("adding to buy list");
    }

    void removeFromBuyList(String jsonString) {
        System.out.println("removing from buy list");
    }

    String getCommodityById(String jsonString) {
        return "getting commodity by id [json format]";
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

    private Provider findProvider(long id) {
        for (Provider provider : this.providers)
            if(provider.getId() == id)
                return provider;
        return null;
    }


}

