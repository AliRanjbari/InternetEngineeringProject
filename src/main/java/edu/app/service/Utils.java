package edu.app.service;

import edu.app.model.CommodityInBuyList.CommodityInBuyList;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<CommodityInBuyList> removeDuplicate (List<CommodityInBuyList> commodities) {
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
