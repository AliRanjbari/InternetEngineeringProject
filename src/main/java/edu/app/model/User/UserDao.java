package edu.app.model.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDao {

    @Autowired
    private UserRepo repo;

    public void save(User user) {
        repo.save(user);
    }

}
