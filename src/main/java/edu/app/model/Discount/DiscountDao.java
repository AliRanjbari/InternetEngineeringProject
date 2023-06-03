package edu.app.model.Discount;

import edu.app.model.Commodity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountDao {

    @Autowired
    private DiscountRepo repo;

    public void save(Discount discount) {
        repo.save(discount);
    }

}
