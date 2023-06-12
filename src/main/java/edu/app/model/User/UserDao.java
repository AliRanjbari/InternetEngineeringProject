package edu.app.model.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserDao {

    @Autowired
    private UserRepo repo;

    public void save(User user) {
        repo.save(user);
    }

    public Optional<User> findByUserName(String UserName) {
        return repo.findByUserName(UserName);
    }

    public void addCredit(long id, int amount) {
        User user = repo.findById(id).get();
        user.addCredit(amount);
        repo.save(user);
    }

}
