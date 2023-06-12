package edu.app.model.Discount;

import edu.app.model.Commodity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiscountDao {

    @Autowired
    private DiscountRepo repo;

    public void save(Discount discount) {
        repo.save(discount);
    }

    public Discount findByDiscountCode(String discountCode){
        try {
            return repo.findByDiscountCode(discountCode).get();
        } catch (Exception e) {
            throw new RuntimeException("Discount not found");
        }
    }
}
