package edu.app;

import edu.app.api.*;
import edu.app.site.Initial;

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

}
