package edu.app.api;

import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;

import java.time.LocalDate;


public class User {
    String userName;
    String password;
    String email;
    LocalDate birthDay;
    String address;
    int Credit;


    public User(String userName, String password, String email, LocalDate birthDay, String address, int credit) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.birthDay = birthDay;
        this.address = address;
        Credit = credit;
    }


    public String toString () {
        return this.userName + " -> " + this.password;
    }

}
