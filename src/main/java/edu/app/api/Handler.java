package edu.app.api;


import java.lang.reflect.Array;

public class Handler {

    DB dataBase;
    public void handleCommand(String input) {
        String[] arguments = input.split(" ");
        String command = arguments[0];

        switch (command) {
            case "addUser":
            {
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
