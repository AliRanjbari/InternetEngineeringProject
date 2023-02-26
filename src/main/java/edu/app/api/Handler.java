package edu.app.api;

import org.json.simple.parser.ParseException;

public class Handler {

    DB dataBase = new DB();
    public void handleCommand(String input) {
        String[] arguments = input.split(" ");

        String command = arguments[0];
        String jsonString = input.substring((command.length() + 1) , (input.length()));


        switch (command) {
            case "addUser":
            {
                try {
                    JsonHandler.addUser(jsonString, dataBase);
                } catch (ParseException e) {
                    System.err.println("error");
                    // throw new RuntimeException(e);
                }
                break;
            }
            case "addProvider":
            {
                dataBase.addProvider(jsonString);
                break;
            }
            case "addCommodity":
            {
                dataBase.addCommodity(jsonString);
                break;
            }
            case "getCommoditiesList":
            {
                System.out.println(dataBase.getCommodityList(jsonString));
                break;
            }
            case "rateCommodity":
            {
                dataBase.rateCommodity(jsonString);
                break;
            }
            case "addToBuyList":
            {
                dataBase.addToBuyList(jsonString);
                break;
            }
            case "removeFromBuyList":
            {
                dataBase.removeFromBuyList(jsonString);
                break;
            }
            case "getCommodityById":
            {
                System.out.println(dataBase.getCommodityById(jsonString));
                break;
            }
            case "getCommoditiesByCategory":
            {
                System.out.println(dataBase.getCommoditiesByCategory(jsonString));
                break;
            }
            case "getBuyList":
            {
                System.out.println(dataBase.getBuyList(jsonString));
                break;
            }

            default:
                System.err.println("Incorrect Command");
        }

    }
}
