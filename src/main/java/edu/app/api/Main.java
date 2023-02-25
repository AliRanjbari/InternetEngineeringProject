package edu.app.api;

import org.json.simple.JSONObject;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        User user = new User("Ali",
                            "123",
                            "ali@gmail.com",
                            LocalDate.parse("2018-12-10"),
                            "iran",
                            2300);
        System.out.println(user.toString());
    }
}

