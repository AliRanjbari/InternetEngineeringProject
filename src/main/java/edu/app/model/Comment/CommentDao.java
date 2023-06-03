package edu.app.model.Comment;

import edu.app.model.Commodity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentDao {

    @Autowired
    private CommentRepo repo;

    public void save(Comment comment) {
        repo.save(comment);
    }

}
