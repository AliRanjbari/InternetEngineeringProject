package edu.app.dao;


import edu.app.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {

    @PersistenceContext
    private EntityManager entityManager

    @Transactional
    public void insert(User user) {
        this.entityManager.persist(user);
    }

}
