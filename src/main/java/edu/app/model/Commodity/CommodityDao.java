package edu.app.model.Commodity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommodityDao {

    @Autowired
    private CommodityRepo repo;

    public void save(Commodity commodity) {
        repo.save(commodity);
    }

    public Optional<Commodity> findById(Long id) {
        return repo.findById(id);
    }

    public List<Commodity> findByInStockGreaterThan (int inStock) {return repo.findByInStockGreaterThan(inStock);}

    public List<Commodity> findAll () {return (List<Commodity>) repo.findAll();}

    public List<Commodity> findByInStockGreaterThanAndNameContaining (int inStock , String name) {return repo.findByInStockGreaterThanAndNameContaining(inStock, name);}

    public List<Commodity> findByNameContaining(String name) {return repo.findByNameContaining(name);}

    // public List<Commodity> findByInStockGreaterThanAndCategoriesContaining(int inStock , String category) {return repo.findByInStockGreaterThanAndCategoriesContaining(inStock , category);}

    // public List<Commodity> findByCategoriesContaining(String category) {return repo.findByCategoriesContaining(category);}


}
