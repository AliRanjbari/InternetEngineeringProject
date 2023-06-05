package edu.app.model.Commodity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityRepo extends CrudRepository<Commodity, Long> {
    List<Commodity> findByInStockGreaterThan(int inStock);
}
