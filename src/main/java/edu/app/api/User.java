package edu.app.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class User {
    private String userName;
    private String password;
    private String email;
    private LocalDate birthDay;
    private String address;
    private List<Commodity> buyList = new ArrayList<Commodity>();
    private long Credit;

    public User(String userName, String password, String email, LocalDate birthDay, String address, long credit) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.birthDay = birthDay;
        this.address = address;
        this.Credit = credit;
    }

    void update(String password, String email, LocalDate birthDay, String address, long credit) {
        this.password = password;
        this.email = email;
        this.birthDay = birthDay;
        this.address = address;
        this.Credit = credit;
    }

    public void addItemToList (Commodity commodity) {
        this.buyList.add(commodity);
    }

    public String toString () {
        return this.userName + " -> " + this.password;
    }

    public String getUserName() {
        return this.userName;
    }
}

//{"username" : "ali" , "password" : "123" , "email" : "1234" , "address" : "sohanak" , "birthDate" : "1977-09-15" , "credit" : 75}