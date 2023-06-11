package edu.app.model.Provider;

import edu.app.model.Commodity.Commodity;
import edu.app.model.Discount.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderDao {

    @Autowired
    private ProviderRepo repo;

    public void save(Provider provider) {
        repo.save(provider);
    }

    public Optional<Provider> findById(Long id) {
        return repo.findById(id);
    }

    public long getIdByName(String name) {
        return repo.findByName(name).get().getId();
    }

    public List<Commodity> getCommodities(long id) {
        return repo.findById(id).get().getCommodities();
    }

    public List<Commodity> getCommodities(String name) {
        return repo.findByName(name).get().getCommodities();
    }
}
