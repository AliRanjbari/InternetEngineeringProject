package edu.app.api;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.*;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class User {
    String userName;
    String password;
    String email;
    LocalDate birthDay;
    String address;
    List<Commodity> commodities = new ArrayList<Commodity>();
    long Credit;

/*    public User(String userName, String password, String email, LocalDate birthDay, String address, int credit) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.birthDay = birthDay;
        this.address = address;
        this.Credit = credit;
    }*/


    // commnet

    public String toString () {
        return this.userName + " -> " + this.password;
    }

    public User(String json_input) throws ParseException {

        Object o = new JSONParser().parse(json_input);


        JSONObject j = (JSONObject) o;

        this.userName = (String) j.get("username");
        this.password = (String) j.get("password");
        this.email = (String) j.get("email");
        this.address = (String) j.get("address");
        this.birthDay = LocalDate.parse((String) j.get("birthDate"));
        this.Credit = (long) j.get("credit");

//new_user

    }


}

//{"username" : "ali" , "password" : "123" , "email" : "1234" , "address" : "sohanak" , "birthDate" : "1977-09-15" , "credit" : 75}