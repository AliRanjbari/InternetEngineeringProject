package edu.app.api;

import org.json.simple.parser.ParseException;

public class Handler {

    DB dataBase = new DB();
    public void handleCommand(String input) {
        String[] arguments = input.split(" ");
        if (arguments.length < 2){
            System.err.println("Command Should be line [command] [jsonString]");
            return;
        }

        String command = arguments[0];
        String jsonString = input.substring((command.length() + 1));

        try {
            switch (command) {
                case "addUser": {
                    JsonHandler.addUser(jsonString, dataBase);
                    break;
                }
                case "addProvider": {
                    dataBase.addProvider(jsonString);
                    break;
                }
                case "addCommodity": {
                    dataBase.addCommodity(jsonString);
                    break;
                }
                case "getCommoditiesList": {
                    System.out.println(dataBase.getCommodityList(jsonString));
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
                    System.out.println(dataBase.getCommodityById(jsonString));
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
