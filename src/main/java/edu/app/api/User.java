package edu.app.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class User {
    String userName;
    String password;
    String email;
    LocalDate birthDay;
    String address;
    List<Commodity> buyList = new ArrayList<Commodity>();
    long Credit;

    public User(String userName, String password, String email, LocalDate birthDay, String address, long credit) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.birthDay = birthDay;
        this.address = address;
        this.Credit = credit;
    }

    public String toString () {
        return this.userName + " -> " + this.password;
    }






}

//{"username" : "ali" , "password" : "123" , "email" : "1234" , "address" : "sohanak" , "birthDate" : "1977-09-15" , "credit" : 75}