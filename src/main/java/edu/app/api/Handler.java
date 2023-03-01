package edu.app.api;

import org.json.simple.parser.ParseException;

public class Handler {

    DB dataBase = new DB();
    public void handleCommand(String input) {
        String[] arguments = input.split(" ");

        String command = arguments[0];
        String jsonString;

        if (command.equals("getCommoditiesList")) {
            jsonString = "";
        }   else {
            if (arguments.length < 2 && !command.equals("getCommoditiesList")){
                System.err.println("Command Should be like [command] <jsonData>");
                return;
            }
            jsonString = input.substring((command.length() + 1));
        }


        try {
            switch (command) {
                case "addUser": {
                    JsonHandler.addUser(jsonString, dataBase);
                    System.out.println("user added");
                    break;
                }
                case "addProvider": {
                    JsonHandler.addProvider(jsonString, dataBase);
                    System.out.println("provider added");
                    break;
                }
                case "addCommodity": {
                    JsonHandler.addCommodity(jsonString, dataBase);
                    System.out.println("commodity added");
                    break;
                }
                case "getCommoditiesList": {
                    System.out.println(JsonHandler.getCommoditiesList(dataBase));
                    break;
                }
                case "rateCommodity": {
                    JsonHandler.rateCommodity(jsonString, dataBase);
                    break;
                }
                case "addToBuyList": {
                    JsonHandler.addToBuyList(jsonString, dataBase);
                    break;
                }
                case "removeFromBuyList": {
                    JsonHandler.removeFromBuyList(jsonString, dataBase);
                    break;
                }
                case "getCommodityById": {
                    System.out.println(JsonHandler.getCommodityById(jsonString , dataBase));
                    break;
                }
                case "getCommoditiesByCategory": {
                    System.out.println(JsonHandler.getCommoditiesByCategory(jsonString, dataBase));
                    break;
                }
                case "getBuyList": {
                    System.out.println(JsonHandler.getBuyList(jsonString, dataBase));
                    break;
                }
                default:
                    System.err.println("Incorrect Command");
            }
        } catch (ParseException e) {
            System.err.println("Wrong json format!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
