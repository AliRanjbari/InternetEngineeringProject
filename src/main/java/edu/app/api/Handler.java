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
            if (arguments.length < 2 && command != "getCommoditiesList"){
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
                    dataBase.rateCommodity(jsonString);
                    break;
                }
                case "addToBuyList": {
                    dataBase.addToBuyList(jsonString);
                    break;
                }
                case "removeFromBuyList": {
                    dataBase.removeFromBuyList(jsonString);
                    break;
                }
                case "getCommodityById": {
                    System.out.println(JsonHandler.getCommodityById(jsonString , dataBase));
                    break;
                }
                case "getCommoditiesByCategory": {
                    System.out.println(dataBase.getCommoditiesByCategory(jsonString));
                    break;
                }
                case "getBuyList": {
                    System.out.println(dataBase.getBuyList(jsonString));
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
