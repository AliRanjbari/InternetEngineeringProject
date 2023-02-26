package edu.app.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.time.LocalDate;


public class JsonHandler {

    static public void addUser(String jsonString, DB dataBase) throws ParseException {

        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        String userName = (String) j.get("username");
        String password = (String) j.get("password");
        String email = (String) j.get("email");
        String address = (String) j.get("address");
        LocalDate birthDay = LocalDate.parse((String) j.get("birthDate"));
        long credit = (long) j.get("credit");

        /*dataBase.addUser(new User(userName, password, email, address, birthDay, credit));*/

    }

}
