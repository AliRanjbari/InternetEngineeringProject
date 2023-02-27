package edu.app.api;


import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import java.time.LocalDate;


public class JsonHandler {
    static String[] addUserJsonVariables = {"username", "password", "email", "address", "birthDate", "credit"};
    static String[] addProviderJsonVariables = {"id", "name", "registryDate"};

    static public void addUser(String jsonString, DB dataBase) throws Exception {

        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(addUserJsonVariables, j);

        String userName = (String) j.get(addUserJsonVariables[0]);
        String password = (String) j.get(addUserJsonVariables[1]);
        String email = (String) j.get(addUserJsonVariables[2]);
        String address = (String) j.get(addUserJsonVariables[3]);
        LocalDate birthDay = LocalDate.parse((String) j.get(addUserJsonVariables[4]));
        long credit = (long) j.get(addUserJsonVariables[5]);

        dataBase.addUser(userName, password, email, birthDay, address, credit);
    }

    static public void addProvider(String jsonString, DB dataBase) throws Exception {
        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(addProviderJsonVariables, j);

        long id = (long) j.get(addProviderJsonVariables[0]);
        String name = (String) j.get(addProviderJsonVariables[1]);
        LocalDate registryDate = LocalDate.parse((String) j.get(addProviderJsonVariables[2]));

        dataBase.addProvider(id, name, registryDate);
    }

    static public void checkVariables(String[] variables, JSONObject j) {
        for (String var : variables)
            if (j.get(var) == null)
                throw new RuntimeException("Variable [" + var + "] not defined");
    }

}
