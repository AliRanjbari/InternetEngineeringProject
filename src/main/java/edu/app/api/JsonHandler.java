package edu.app.api;


import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.time.LocalDate;
import java.util.Map;


public class JsonHandler {

    static public void addUser(String jsonString, DB dataBase) throws Exception {

        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        String userName = (String) j.get("username");
        String password = (String) j.get("password");
        String email = (String) j.get("email");
        String address = (String) j.get("address");
        LocalDate birthDay = LocalDate.parse((String) j.get("birthDate"));
        long credit = (long) j.get("credit");

        if(userName == null || password == null || email == null || address == null){
            throw new RuntimeException("Must define all variables");
        }

        dataBase.addUser(userName, password, email, birthDay, address, credit);
    }

}
