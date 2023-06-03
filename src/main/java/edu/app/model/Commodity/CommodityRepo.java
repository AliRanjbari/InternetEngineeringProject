package edu.app.model.Commodity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommodityRepo extends CrudRepository<Commodity, Long> {
}
