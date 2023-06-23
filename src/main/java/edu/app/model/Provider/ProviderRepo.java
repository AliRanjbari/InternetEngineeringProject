package edu.app.model.Provider;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ProviderRepo extends CrudRepository<Provider, Long> {

    public Optional<Provider> findByName(String name);
}
