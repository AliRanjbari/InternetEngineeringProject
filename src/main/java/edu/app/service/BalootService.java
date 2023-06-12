package edu.app.service;

import edu.app.model.CommodityInBuyList.CommodityInBuyList;
import edu.app.model.User.User;
import java.util.ArrayList;
import java.util.List;

public class BalootService {
    private static BalootService instance;
    User loggedUser;

    private BalootService() throws Exception {
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

    public void login(User user, String inputPassword) {
        if (user == null)
            throw new RuntimeException("Wrong username");
        if (!user.getPassword().equals(inputPassword))
            throw new RuntimeException("Wrong password");

        this.loggedUser = user;
    }

    public void logout() {
        this.loggedUser = null;
    }

    public List<CommodityInBuyList> removeDuplicate (List<CommodityInBuyList> commodities) {
        List<Long> commodityIds = new ArrayList<>();
        List<CommodityInBuyList> newCommodities = new ArrayList<>();
        for (int i = 0; i < commodities.size(); i++) {
            if (!commodityIds.contains(commodities.get(i).getId()))
                    commodityIds.add(commodities.get(i).getId());
        }

            for (int j = 0; j < commodityIds.size(); j++){
                for (int i = 0; i < commodities.size();i++) {
                    if (commodities.get(i).getId() == commodityIds.get(j)) {
                        newCommodities.add(commodities.get(i));
                        break;
                    }
                }
            }
        return newCommodities;
    }
}
