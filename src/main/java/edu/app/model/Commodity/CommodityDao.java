package edu.app.model.Commodity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.min;

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

    public List<Commodity> findByInStockGreaterThanAndNameContaining (int inStock , String name) {return repo.findByInStockGreaterThanAndNameContaining(inStock, name);}

    public List<Commodity> findByName(String name) {return repo.findByNameContaining(name);}

    public List<Commodity> findByName(String name, boolean justAvailable, int sortOption) {
        if (justAvailable) {
            if (sortOption == 1) {
                return repo.findByInStockGreaterThanAndNameContaining(0, name, Sort.by(Sort.Direction.DESC, "price"));
            } else if (sortOption == 2) {
                return repo.findByInStockGreaterThanAndNameContaining(0, name, Sort.by(Sort.Direction.ASC, "name"));
            }
            else {
                return repo.findByInStockGreaterThan(0);
            }
        } else {
            if (sortOption == 1) {
                return repo.findByNameContaining(name, Sort.by(Sort.Direction.DESC, "price"));
            } else if (sortOption == 2) {
                return repo.findByNameContaining(name, Sort.by(Sort.Direction.ASC, "name"));
            }
            else {
                return repo.findAll();
            }
        }
    }

    public List<Commodity> findAll () {return (List<Commodity>) repo.findAll();}

    public List<Commodity> findAll(boolean justAvailable, int sortOption) {
        if (justAvailable) {
            if (sortOption == 1) {
                return repo.findByInStockGreaterThan(0, Sort.by(Sort.Direction.DESC, "price"));
            } else if (sortOption == 2) {
                return repo.findByInStockGreaterThan(0, Sort.by(Sort.Direction.ASC, "name"));
            }
            else {
                return repo.findByInStockGreaterThan(0);
            }
        } else {
            if (sortOption == 1) {
                return repo.findAll(Sort.by(Sort.Direction.DESC, "price"));
            } else if (sortOption == 2) {
                return repo.findAll(Sort.by(Sort.Direction.ASC, "name"));
            }
            else {
                return repo.findAll();
            }
        }
    }

    public static List<Commodity> paginate(int PageNum , List<Commodity> commodities){
        return commodities.subList((PageNum - 1) * 12 ,  min(((PageNum)* 12 ) , commodities.size()));
    }

    // public List<Commodity> findByInStockGreaterThanAndCategoriesContaining(int inStock , String category) {return repo.findByInStockGreaterThanAndCategoriesContaining(inStock , category);}

    // public List<Commodity> findByCategoriesContaining(String category) {return repo.findByCategoriesContaining(category);}
}
