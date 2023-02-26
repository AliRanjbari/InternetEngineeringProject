package edu.app.api;

public class Handler {

    DB dataBase = new DB();
    public void handleCommand(String input) {
        String[] arguments = input.split(" ");
        if (arguments.length != 2){
            System.err.println("Incorrect format have to be like [command] [json]");
            return;
        }

        String command = arguments[0];
        String jsonString = arguments[1];

        switch (command) {
            case "addUser":
            {
                dataBase.addUser(jsonString);
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
