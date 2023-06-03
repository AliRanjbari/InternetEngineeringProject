package edu.app.repository;


import edu.app.model.Comment.Comment;
import edu.app.model.Commodity.Commodity;
import edu.app.model.Discount.Discount;
import edu.app.model.Provider.Provider;
import edu.app.model.User.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class JsonHandler {
    final private static String[] addUserJsonVariables = {"username", "password", "email", "address", "birthDate", "credit"};
    final private static String[] addProviderJsonVariables = {"id", "name", "registryDate", "image"};
    final private static String[] addCommodityJsonVariables = {"id", "name", "providerId", "price", "categories", "rating", "inStock", "image"};
    final private static String[] rateCommodityJsonVariables = {"username", "commodityId", "score"};
    final private static String[] addToBuyListJsonVariable = {"username", "commodityId"};
    final private static String[] removeFromBuyListJsonVariable = {"username", "commodityId"};
    final private static String[] getCommodityByIdJsonVariables = {"id"};
    final private static String[] getCommoditiesByCategoryJsonVariables = {"category"};
    final private static String[] getBuyListJsonVariables = {"username"};
    final private static String[] addCommentJsonVariables = {"userEmail", "commodityId", "text", "date"};
    final private static String[] addDiscountJsonVariables = {"discountCode" , "discount"};

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
    static public User parseUser(String jsonString) throws Exception {

        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(addUserJsonVariables, j);

        String userName = (String) j.get(addUserJsonVariables[0]);
        String password = (String) j.get(addUserJsonVariables[1]);
        String email = (String) j.get(addUserJsonVariables[2]);
        String address = (String) j.get(addUserJsonVariables[3]);
        LocalDate birthDay = LocalDate.parse((String) j.get(addUserJsonVariables[4]));
        long credit = (long) j.get(addUserJsonVariables[5]);

        return new User(userName, password, email, birthDay, address, credit);
    }


    static public void addProvider(String jsonString, DB dataBase) throws Exception {
        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(addProviderJsonVariables, j);

        long id = (long) j.get(addProviderJsonVariables[0]);
        String name = (String) j.get(addProviderJsonVariables[1]);
        LocalDate registryDate = LocalDate.parse((String) j.get(addProviderJsonVariables[2]));
        String ImgUrl = (String) j.get(addProviderJsonVariables[3]);

        dataBase.addProvider(id, name, registryDate, ImgUrl);
    }

    static public Provider parseProvider(String jsonString) throws Exception {
        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(addProviderJsonVariables, j);

        long id = (long) j.get(addProviderJsonVariables[0]);
        String name = (String) j.get(addProviderJsonVariables[1]);
        LocalDate registryDate = LocalDate.parse((String) j.get(addProviderJsonVariables[2]));
        String ImgUrl = (String) j.get(addProviderJsonVariables[3]);

        return new Provider (id, name, registryDate, ImgUrl);
    }

    static public void addComment(String jsonString, DB dataBase) throws Exception {
        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(addCommentJsonVariables, j);

        String email = (String) j.get(addCommentJsonVariables[0]);
        long commodityId = (long) j.get(addCommentJsonVariables[1]);
        String text = (String) j.get(addCommentJsonVariables[2]);
        LocalDate commentDate = LocalDate.parse((String) j.get(addCommentJsonVariables[3]));

        dataBase.addComment(email , commodityId , text , commentDate);
    }

    static public Comment parseComment(String jsonString) throws Exception {
        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(addCommentJsonVariables, j);

        String email = (String) j.get(addCommentJsonVariables[0]);
        long commodityId = (long) j.get(addCommentJsonVariables[1]);
        String text = (String) j.get(addCommentJsonVariables[2]);
        LocalDate commentDate = LocalDate.parse((String) j.get(addCommentJsonVariables[3]));

        return new Comment (commodityId , email , text , commentDate);
    }
    static public void addCommodity(String jsonString, DB dataBase) throws Exception {
        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(addCommodityJsonVariables, j);
        long id = (long) j.get(addCommodityJsonVariables[0]);
        String name = (String) j.get(addCommodityJsonVariables[1]);
        long providerId = (long) j.get(addCommodityJsonVariables[2]);
        long price = (long) j.get(addCommodityJsonVariables[3]);
        ArrayList<String> categories = (ArrayList<String>) j.get(addCommodityJsonVariables[4]);
        double rating = (double) j.get(addCommodityJsonVariables[5]);
        long inStock = (long) j.get(addCommodityJsonVariables[6]);
        String imgURL = (String) j.get(addCommodityJsonVariables[7]);

        dataBase.addCommodity(id, name, providerId, price, categories, rating, inStock, imgURL);
    }

    static public Commodity parseCommodity(String jsonString) throws Exception {
        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(addCommodityJsonVariables, j);
        long id = (long) j.get(addCommodityJsonVariables[0]);
        String name = (String) j.get(addCommodityJsonVariables[1]);
        long providerId = (long) j.get(addCommodityJsonVariables[2]);
        long price = (long) j.get(addCommodityJsonVariables[3]);
        ArrayList<String> categories = (ArrayList<String>) j.get(addCommodityJsonVariables[4]);
        double rating = (double) j.get(addCommodityJsonVariables[5]);
        long inStock = (long) j.get(addCommodityJsonVariables[6]);
        String imgURL = (String) j.get(addCommodityJsonVariables[7]);

        return new Commodity(id, name, providerId, price, categories, rating, inStock, imgURL);
    }

    static public void addDiscount(String jsonString, DB database) throws Exception {
        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(addDiscountJsonVariables, j);
        String discountCode = (String) j.get(addDiscountJsonVariables[0]);
        long discount = (long) j.get(addDiscountJsonVariables[1]);

        database.addDiscount(discountCode , discount);

    }
    static public Discount parseDiscount(String jsonString) throws Exception {
        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(addDiscountJsonVariables, j);
        String discountCode = (String) j.get(addDiscountJsonVariables[0]);
        long discount = (long) j.get(addDiscountJsonVariables[1]);

        return new Discount(discountCode , discount);

    }


    static public String getCommoditiesList(DB database) throws ParseException {
        List<Commodity> commodities = database.getCommodities();
        return commoditiesToJsonString(commodities);
    }

    static public void rateCommodity(String jsonString, DB database) throws ParseException {
        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(rateCommodityJsonVariables, j);

        String username = j.get(rateCommodityJsonVariables[0]).toString();
        long commodityId = (long) j.get(rateCommodityJsonVariables[1]);
        double score = (double) (long) j.get(rateCommodityJsonVariables[2]);

        database.rateCommodity(username, commodityId, score);
    }

    static public void addToBuyList (String jsonString, DB database) throws ParseException {
        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(addToBuyListJsonVariable, j);

        String username = j.get(addToBuyListJsonVariable[0]).toString();
        long commodityId = (long) j.get(addToBuyListJsonVariable[1]);

        database.addToBuyList(username, commodityId);
    }

    static public void removeFromBuyList (String jsonString, DB database) throws ParseException {
        Object o = new JSONParser().parse(jsonString);
        JSONObject j = (JSONObject) o;

        checkVariables(removeFromBuyListJsonVariable, j);

        String username = j.get(removeFromBuyListJsonVariable[0]).toString();
        long commodityId = (long) j.get(removeFromBuyListJsonVariable[1]);

        database.removeFromBuyList(username, commodityId);
    }

    static public String getCommodityById(String jsonString, DB database) throws ParseException {
        Object o = new JSONParser().parse(jsonString);
        JSONObject in = (JSONObject) o;
        JSONObject out = new JSONObject();

        checkVariables(getCommodityByIdJsonVariables, in);
        long id = (long) in.get(getCommodityByIdJsonVariables[0]);
        Commodity commodity = database.findCommodity(id);
        if (commodity == null)
            throw new RuntimeException("commodity has not found");
        String providerName = database.findProvider(commodity.getProviderId()).getName();
        out.put("id", commodity.getId());
        out.put("name", commodity.getName());
        out.put("provider", providerName);
        out.put("price", commodity.getPrice());
        out.put("categories", commodity.getCategories());
        out.put("rating", commodity.getRating());
        out.put("inStock", commodity.getInStock());

        return out.toString();
    }

    static public String getCommoditiesByCategory (String jsonString, DB database) throws ParseException {
        // getting input
        Object o = new JSONParser().parse(jsonString);
        JSONObject in = (JSONObject) o;


        checkVariables(getCommoditiesByCategoryJsonVariables, in);
        String category = in.get(getCommoditiesByCategoryJsonVariables[0]).toString();
        List<Commodity> commodities = database.getCommoditiesByCategory(category);


        return commoditiesToJsonString(commodities);
    }

    static public String getBuyList (String jsonString, DB database) throws ParseException {
        Object o = new JSONParser().parse(jsonString);
        JSONObject in = (JSONObject) o;

        checkVariables(getBuyListJsonVariables, in);
        String category = in.get(getBuyListJsonVariables[0]).toString();
        List<Commodity> commodities = database.getBuyList(category);

        return commoditiesToJsonString(commodities);
    }

    static private String commoditiesToJsonString (List<Commodity> commodities) {
        JSONObject out = new JSONObject();

        List<JSONObject> commoditiesJson = new ArrayList<JSONObject>();

        for (Commodity commodity : commodities) {
            JSONObject commodityJson = new JSONObject();
            commodityJson.put("id", commodity.getId());
            commodityJson.put("name", commodity.getName());
            commodityJson.put("providerId", commodity.getProviderId());
            commodityJson.put("price", commodity.getPrice());
            commodityJson.put("categories", commodity.getCategories());
            commodityJson.put("rating", commodity.getRating());
            commodityJson.put("inStock", commodity.getInStock());

            commoditiesJson.add(commodityJson);
        }

        out.put("commoditiesListByCategory", commoditiesJson);
        return out.toString();
    }

     static private void checkVariables(String[] variables, JSONObject j) {
        for (String var : variables)
            if (j.get(var) == null)
                throw new RuntimeException("Variable \"" + var + "\" not defined");
    }


}
