package edu.app.model.Commodity;

import org.junit.runner.manipulation.Sortable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityRepo extends JpaRepository<Commodity, Long> {

    List<Commodity> findByInStockGreaterThan(int inStock);
    List<Commodity> findByInStockGreaterThan(int inStock, Sort sort);

    List<Commodity> findByInStockGreaterThanAndNameContaining(int inStock, String name);
    List<Commodity> findByInStockGreaterThanAndNameContaining(int inStock, String name, Sort sort);

    List<Commodity> findByNameContaining(String name);
    List<Commodity> findByNameContaining(String name, Sort sort);



    // List<Commodity> findByInStockGreaterThanAndCategoriesContaining(int inStock, String category);

    //List<Commodity> findByCategoriesContaining(String searchString);
}
