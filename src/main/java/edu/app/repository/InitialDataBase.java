package edu.app.repository;

import edu.app.model.Comment.Comment;
import edu.app.model.Comment.CommentDao;
import edu.app.model.Commodity.Commodity;
import edu.app.model.Commodity.CommodityDao;
import edu.app.model.Discount.Discount;
import edu.app.model.Discount.DiscountDao;
import edu.app.model.Provider.Provider;
import edu.app.model.Provider.ProviderDao;
import edu.app.model.User.User;
import edu.app.model.User.UserDao;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static edu.app.repository.JsonHandler.*;


@Component
public class InitialDataBase implements ApplicationRunner {
     static String baseUrl = "http://5.253.25.110:5000/api";

     @Autowired
     private UserDao userDao;
     @Autowired
     private CommodityDao commodityDao;

     @Autowired
     private ProviderDao providerDao;

     @Autowired
     private CommentDao commentDao;

     @Autowired
     private DiscountDao discountDao;

    public static  void initDatabase(DB database) throws Exception {
        getUsers(database);
        getProviders(database);
        getCommodities(database);
        //getComments(database);
        getDiscounts(database);
    }

    private static void getUsers(DB database) throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/users").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for (Object value : jasonInput) addUser(value.toString(), database);
    }

    private void getUsers() throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/users").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for(int i = 0; i < jasonInput.size() ; i++) {
            User newUser = parseUser(jasonInput.get(i).toString());
            this.userDao.save(newUser);
        }
    }


    private static void getCommodities(DB database) throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/v2/commodities").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for (Object value : jasonInput) addCommodity(value.toString(), database);
    }

    private void getCommodities() throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/v2/commodities").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for(int i = 0; i < jasonInput.size() ; i++) {
            Commodity newCommodity = parseCommodity(jasonInput.get(i).toString());
            this.commodityDao.save(newCommodity);
        }
    }

    private static void getProviders(DB database) throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/v2/providers").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for (Object value : jasonInput) addProvider(value.toString(), database);
    }
    private void getProviders() throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/v2/providers").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for(int i = 0; i < jasonInput.size() ; i++){
            Provider newProvider = parseProvider(jasonInput.get(i).toString());
            this.providerDao.save(newProvider);
        }
    }


    private static void getComments(DB database) throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/comments").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for (Object value : jasonInput) addComment(value.toString(), database);
    }

    private  void getComments() throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/comments").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for(int i = 0; i < jasonInput.size() ; i++){
            Comment newComment = parseComment(jasonInput.get(i).toString());
            this.commentDao.save(newComment);
        }

    }

    private static void getDiscounts(DB database) throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/discount").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for(int i = 0; i < jasonInput.size() ; i++)
            addDiscount(jasonInput.get(i).toString(), database);
    }
    private void getDiscounts() throws Exception {
        String stringInput = Jsoup.connect(baseUrl + "/discount").ignoreContentType(true).execute().body();
        Object o = new JSONParser().parse(stringInput);
        JSONArray jasonInput = (JSONArray) o;

        for(int i = 0; i < jasonInput.size() ; i++) {
            Discount newDiscount = parseDiscount(jasonInput.get(i).toString());
            this.discountDao.save(newDiscount);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Initializing database ... ");
        System.out.println("Getting Users... ");
        getUsers();
        System.out.println("Getting Providers... ");
        getProviders();
        System.out.println("Getting Commodities... ");
        getCommodities();
        //System.out.println("Getting Comments... ");
        //getComments();
        System.out.println("Getting Discounts... ");
        getDiscounts();
    }
}
