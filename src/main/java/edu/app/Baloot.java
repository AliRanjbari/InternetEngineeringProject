package edu.app;

import edu.app.api.*;
import edu.app.site.Initial;

import java.util.List;

public class Baloot {

    private static Baloot instance;
    User loggedUser;
    DB database;

    private Baloot() throws Exception {
        this.database = new DB();
        Initial.initDatabase(this.database);

        // delete
        this.loggedUser = this.database.findUser("amir");
        this.loggedUser.addItemToList(this.database.findCommodity(1));
        database.useDiscount(loggedUser, "HAPPY_NOWRUZ");
    }

    public static Baloot getInstance() throws Exception{
        if (instance == null) {
            instance = new Baloot();
        }
        return instance;
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }

    public void login(String username, String password) {
        User user = this.database.findUser(username);
        if (user == null)
            throw new RuntimeException("Wrong username");
        if (!user.getPassword().equals(password))
            throw new RuntimeException("Wrong password");

        this.loggedUser = user;
    }

    public void logout() {
        this.loggedUser = null;
    }

    public List<Commodity> getCommodities() {
        return this.database.getCommodities();
    }

    public List<Commodity> getCommoditiesByCategory(String categoryName) {
        return this.database.getCommoditiesByCategory(categoryName);
    }

    public List<Commodity> getCommoditiesByName(String name) {
        return this.database.getCommoditiesByName(name);
    }

    public List<Commodity> getCommoditiesSortByPrice() {
        return this.database.getCommoditiesSortByPrice();
    }

    public String getProviderNameById(long id) {
        return this.database.findProvider(id).getName();
    }

    public DB getDatabase() {
        return database;
    }

}
