package edu.app.model.CommodityInBuyList;

import edu.app.model.Commodity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommodityInBuyListDao {

    @Autowired
    private CommodityInBuyListRepo repo;

    public void save(CommodityInBuyList commodityInBuyList) {
        repo.save(commodityInBuyList);
    }

}
