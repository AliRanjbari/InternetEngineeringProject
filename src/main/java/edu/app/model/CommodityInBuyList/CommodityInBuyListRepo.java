package edu.app.model.CommodityInBuyList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommodityInBuyListRepo extends CrudRepository <CommodityInBuyList, Long> {
}
