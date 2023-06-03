package edu.app.model.Provider;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderRepo extends CrudRepository<Provider, Long> {
}
