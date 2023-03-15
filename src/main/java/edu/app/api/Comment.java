package edu.app.api;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Comment {
    private String email ;

    private String text;
    private long rate;
    private LocalDate commentDate;

    private Map<String, Long> userRates = new HashMap<String, Long>();

    public Comment(String email, String text, LocalDate commentDate) {
        this.email = email;
        this.text = text;
        this.rate =  0;
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
            userRates.put(userName, score);
            this.rate = (this.rate - rateToRemove) + score;

        } else {
            this.rate = this.rate + score;
            userRates.put(userName, score);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }

    public long getRate() {
        return rate;
    }

    public LocalDate getCommentDate() {
        return commentDate;
    }
}
