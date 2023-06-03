package edu.app.model.Discount;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepo extends CrudRepository<Discount , Long> {
}
