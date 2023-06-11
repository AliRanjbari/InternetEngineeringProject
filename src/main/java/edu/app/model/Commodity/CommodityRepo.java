package edu.app.model.Commodity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityRepo extends JpaRepository<Commodity, Long> {

    List<Commodity> findByInStockGreaterThan(int inStock);
    List<Commodity> findByInStockGreaterThanAndNameContaining(int inStock, String name);

    List<Commodity> findByNameContaining(String name);



    // List<Commodity> findByInStockGreaterThanAndCategoriesContaining(int inStock, String category);

    //List<Commodity> findByCategoriesContaining(String searchString);
}
