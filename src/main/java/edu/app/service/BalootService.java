package edu.app.service;

import edu.app.api.Commodity;
import edu.app.api.DB;
import edu.app.api.User;
import edu.app.site.Initial;

import java.util.List;

public class BalootService {
    private static BalootService instance;
    User loggedUser;
    DB database;

    private BalootService() throws Exception {
        this.database = new DB();
        Initial.initDatabase(this.database);
    }

    public static BalootService getInstance() throws Exception{
        if (instance == null) {
            instance = new BalootService();
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

    public List<Commodity> getMostSimilarCommodities(Commodity commodity) {
        return database.getMostSimilarCommodities(commodity);
    }

}
