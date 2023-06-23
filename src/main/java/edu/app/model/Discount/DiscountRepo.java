package edu.app.model.Discount;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountRepo extends CrudRepository<Discount , Long> {

    public Optional<Discount> findByDiscountCode(String discountCode);

}
