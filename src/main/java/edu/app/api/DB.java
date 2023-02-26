package edu.app.api;

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
}
