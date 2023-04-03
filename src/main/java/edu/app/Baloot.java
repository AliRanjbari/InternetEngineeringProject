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


}
