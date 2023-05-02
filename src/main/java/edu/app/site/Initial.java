package edu.app.site;

import edu.app.api.DB;
import edu.app.api.Discount;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;

import java.io.IOException;

import static edu.app.api.JsonHandler.*;

public class Initial {
     static String baseUrl = "http://5.253.25.110:5000/api/v2";

    public static  void initDatabase(DB database) throws Exception {
        //getUsers(database);
        getProviders(database);
        getCommodities(database);
        // getComments(database);
        //getDiscounts(database);
    }
    private static void getUsers(DB database) throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/users").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for(int i = 0; i < jasonInput.size() ; i++)
            addUser(jasonInput.get(i).toString(), database);
    }

    private static void getCommodities(DB database) throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/commodities").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for(int i = 0; i < jasonInput.size() ; i++)
            addCommodity(jasonInput.get(i).toString(), database);
    }

    private static void getProviders(DB database) throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/providers").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for(int i = 0; i < jasonInput.size() ; i++)
            addProvider(jasonInput.get(i).toString(), database);
    }

    private static void getComments(DB database) throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/comments").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for(int i = 0; i < jasonInput.size() ; i++)
            addComment(jasonInput.get(i).toString(), database);
    }

    private static void getDiscounts(DB database) throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/discount").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for(int i = 0; i < jasonInput.size() ; i++)
            addDiscount(jasonInput.get(i).toString(), database);
    }

}
