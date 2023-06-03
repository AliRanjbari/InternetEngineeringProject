package edu.app.model.Commodity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommodityDao {

    @Autowired
    private CommodityRepo repo;

    public void save(Commodity commodity) {
        repo.save(commodity);
    }
}
