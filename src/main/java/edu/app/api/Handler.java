package edu.app.api;


import org.json.simple.parser.ParseException;

import java.lang.reflect.Array;

public class Handler {

    DB dataBase;
    public void handleCommand(String input) throws ParseException {
        String[] arguments = input.split(" ");
        String command = arguments[0];
        String jason_data = input.substring((command.length() + 1) , input.length());

        switch (command) {
            case "addUser":
            {
                User user = new User(jason_data);
                System.out.println(user.userName);
                System.out.println(user.address);
                break;
            }
            case "addProvider":
            {
                break;
            }
            case "addCommodity":
            {
                break;
            }
            case "getCommoditiesList":
            {
                break;
            }
            case "rateCommodity":
            {
                break;
            }
            case "addToBuyList":
            {
                break;
            }
            case "removeFromBuyList":
            {
                break;
            }
            case "getCommodityById":
            {
                break;
            }
            case "getCommodityByCategory":
            {
                break;
            }
            case "getBuyList":
            {
                break;
            }

        }

    }
}
