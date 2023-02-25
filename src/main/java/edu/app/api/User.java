package edu.app.api;

import java.time.LocalDate;

// test
// ali kunie

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
