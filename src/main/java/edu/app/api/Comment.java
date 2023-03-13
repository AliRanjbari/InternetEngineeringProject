package edu.app.api;

import java.time.LocalDate;

public class Comment {
    private String email ;

    private String text;
    private long rate;
    private LocalDate commentDate;

    public Comment(String email, String text, LocalDate commentDate) {
        this.email = email;
        this.text = text;
        this.rate =  0;
        this.commentDate = commentDate;
    }
}
