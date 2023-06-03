package edu.app.model.Provider;

import edu.app.model.Discount.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderDao {

    @Autowired
    private ProviderRepo repo;

    public void save(Provider provider) {
        repo.save(provider);
    }

}
