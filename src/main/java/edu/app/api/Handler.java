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


        boolean success = false;
        String data = "";
        try {
            switch (command) {
                case "addUser": {
                    JsonHandler.addUser(jsonString, dataBase);
                    break;
                }
                case "addProvider": {
                    JsonHandler.addProvider(jsonString, dataBase);
                    break;
                }
                case "addCommodity": {
                    JsonHandler.addCommodity(jsonString, dataBase);
                    break;
                }
                case "getCommoditiesList": {
                    data = JsonHandler.getCommoditiesList(dataBase);
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
                    data = JsonHandler.getCommodityById(jsonString , dataBase);
                    break;
                }
                case "getCommoditiesByCategory": {
                    data = JsonHandler.getCommoditiesByCategory(jsonString, dataBase);
                    break;
                }
                case "getBuyList": {
                    data = JsonHandler.getBuyList(jsonString, dataBase);
                    break;
                }
                default:
                    System.err.println("Incorrect Command");
            }
            success = true;
        } catch (ParseException e) {
            data = "Wrong json format!";
        } catch (Exception e) {
            data = e.getMessage();
        }



        // output message
        if (success) {
            System.out.println("{\"success\": true, \"data\":" + data + "}");

        } else {
            System.out.println("{\"success\": false, \"data\": \"" + data + "\"}");
        }

    }
}
