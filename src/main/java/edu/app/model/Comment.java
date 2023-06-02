package edu.app.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;
    private String username ;
    private String text;
    private long likes;
    private LocalDate commentDate;
    @ElementCollection
    @CollectionTable(name="user_rate_comments",
        joinColumns = {@JoinColumn(name = "comment_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "username")
    @Column(name = "rate")
    private Map<String, Long> userRates = new HashMap<String, Long>();
    @ManyToOne
    @JoinColumn(name = "COMMODITY_ID")
    private Commodity commodity;

    public Comment(long id, String username, String text, LocalDate commentDate) {
        this.id = id;
        this.username = username;
        this.text = text;
        this.likes =  0;
        this.commentDate = commentDate;
    }

    public void rate(String userName , long score){
        if(score > 1)
            throw new RuntimeException("Score is more than 1");
        else if(score < -1)
            throw new RuntimeException("Score os lower than -1");
        else if ( (-1 < score ) && ( score < 1 )&& (score != 0) ) {
            throw new RuntimeException("Score should be 1 or -1 or 0");
        }

        if (userRates.containsKey(userName)) {
            long rateToRemove = userRates.remove(userName);
            if (rateToRemove == 1)
                this.likes -= 1;
        }

        this.userRates.put(userName, score);
        if (score == 1)
            this.likes += 1;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public long getLikes() {
        return this.likes;
    }
    public long getDislikes() {return this.userRates.size() - this.likes;}

    public LocalDate getCommentDate() {
        return commentDate;
    }

    public long getId() {
        return id;
    }
}