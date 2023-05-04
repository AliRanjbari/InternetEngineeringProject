package edu.app.service;

import edu.app.api.Commodity;
import edu.app.api.DB;
import edu.app.api.User;
import edu.app.site.Initial;

import java.time.LocalDate;
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

    public void Register(String userName, String password, String email, LocalDate birthDay, String address){
        if (this.database.findUser(userName) != null)
            throw new RuntimeException("This userName is already taken");
        else if (this.database.findUserByEmail(email) != null)
            throw new RuntimeException("This email is already taken");
        else {
            User user = new User(userName, password, email, birthDay, address, 0);
            this.database.addUser(user);
        }
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

    public List<Commodity> getCommoditiesByCategoryAndList(String categoryName , List<Commodity> commodities) {
        return this.database.getCommoditiesByCategoryAndList(categoryName, commodities);
    }

    public List<Commodity> getCommoditiesByName(String name) {
        return this.database.getCommoditiesByName(name);
    }

    public List<Commodity> getCommoditiesByNameAndList(String name , List<Commodity> commodities) {
        return this.database.getCommoditiesByNameAndList(name , commodities);
    }

    public List<Commodity> getCommoditiesByProviderAndList(String name , List<Commodity> commodities) {
        return this.database.getCommoditiesByProviderAndList(name , commodities);
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
