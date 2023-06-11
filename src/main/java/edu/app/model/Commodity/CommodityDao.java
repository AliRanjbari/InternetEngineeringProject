package edu.app.model.Commodity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.min;

@Service
public class CommodityDao {

    static int COMMODITY_PER_PAGE = 12;

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

    public List<Commodity> findByProviderId(long providerId, boolean justAvailable, int sortOption) {
        if (justAvailable) {
            if (sortOption == 1) {
                return repo.findByInStockGreaterThanAndProviderId(0, providerId, Sort.by(Sort.Direction.DESC, "price"));
            } else if (sortOption == 2) {
                return repo.findByInStockGreaterThanAndProviderId(0, providerId, Sort.by(Sort.Direction.ASC, "name"));
            }
            else {
                return repo.findByInStockGreaterThanAndProviderId(0, providerId);
            }
        } else {
            if (sortOption == 1) {
                return repo.findByProviderId(providerId, Sort.by(Sort.Direction.DESC, "price"));
            } else if (sortOption == 2) {
                return repo.findByProviderId(providerId, Sort.by(Sort.Direction.ASC, "name"));
            }
            else {
                return repo.findByProviderId(providerId);
            }
        }
    }

    public List<Commodity> findByCategory(String categoryName, boolean justAvailable, int sortOption) {
        List<Commodity> allCommodities = this.findAll(justAvailable, sortOption);
        List<Commodity> listCommodityByCategory = new ArrayList<Commodity>();
        for (Commodity commodity : allCommodities)
            if (commodity.doesCategoryExists(categoryName))
                listCommodityByCategory.add(commodity);

        return listCommodityByCategory;
    }

    public List<Commodity> findByName(String name) {return repo.findByNameContaining(name);}

    public List<Commodity> findByName(String name, boolean justAvailable, int sortOption) {
        if (justAvailable) {
            if (sortOption == 1) {
                return repo.findByInStockGreaterThanAndNameContaining(0, name, Sort.by(Sort.Direction.DESC, "price"));
            } else if (sortOption == 2) {
                return repo.findByInStockGreaterThanAndNameContaining(0, name, Sort.by(Sort.Direction.ASC, "name"));
            }
            else {
                return repo.findByInStockGreaterThanAndNameContaining(0, name);
            }
        } else {
            if (sortOption == 1) {
                return repo.findByNameContaining(name, Sort.by(Sort.Direction.DESC, "price"));
            } else if (sortOption == 2) {
                return repo.findByNameContaining(name, Sort.by(Sort.Direction.ASC, "name"));
            }
            else {
                return repo.findByNameContaining(name);
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
        return commodities.subList((PageNum - 1) * CommodityDao.COMMODITY_PER_PAGE ,  min(((PageNum) * CommodityDao.COMMODITY_PER_PAGE ) , commodities.size()));
    }

    public void rateCommodity(String username, long commodityId, double score) {
        Commodity commodity = repo.findById(commodityId).get();
        commodity.rate(username, score);
    }

}
