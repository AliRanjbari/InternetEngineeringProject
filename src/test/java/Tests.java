import edu.app.api.*;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tests {

    private Commodity commodity1 , commodity2;
    private Provider provider1 , provider2;
    private User user1, user2;
    private DB dataBase;


    @BeforeEach
    public void initTest() {
        dataBase = new DB();

        commodity1 = new Commodity(1, "shoes", 1, 300,
                new ArrayList<>(Arrays.asList("clothes", "feet")), 0, 10);

        commodity2 = new Commodity(2, "hats", 2, 300,
                new ArrayList<>(Arrays.asList("clothes", "head")), 0, 10);

        provider1 = new Provider(1,"Provider1", LocalDate.of(2023,10,10));
        provider2 = new Provider(2,"Provider2", LocalDate.of(2023,10,10));

        user1 = new User("user1","1234","email1.com",
                        LocalDate.of(1999,1,1),"sohanak",10000);
        user2 = new User("user2","1234","email2.com",
                LocalDate.of(1999,1,1),"tajrish",10000);
        dataBase.addProvider(provider1);
        dataBase.addProvider(provider2);
        dataBase.addCommodity(commodity1);
        dataBase.addCommodity(commodity2);
        dataBase.addUser(user1);
        dataBase.addUser(user2);
    }

    @Test
    public void testCommodityRate(){
        commodity1.rate("matin" , 5);
        commodity1.rate("ali" , 7);
        assertEquals(6,commodity1.getRating());
    }

    @Test
    public void testDuplicateRate(){
        commodity1.rate("ali" , 4);
        commodity1.rate("majid" , 10);
        commodity1.rate("matin" , 10);
        //till now the score is 8
        commodity1.rate("ali" , 1);
        assertEquals(7,commodity1.getRating());
        //now the score should be 7 because ali's score has changed
    }

    @Test
    public void testGetCommodityById() throws ParseException {
        String jsonString = "{\"id\" : 2}";
        assertEquals("{\"provider\":\"Provider2\",\"price\":300," +
                                "\"name\":\"hats\",\"rating\":0.0,\"inStock\":10," +
                                "\"id\":2,\"categories\":[\"clothes\",\"head\"]}" ,
                                    JsonHandler.getCommodityById(jsonString , dataBase));

    }

    @Test
    public void addToBuyListUserTest() throws ParseException {
        String jsonString = "{\"username\": \"user1\", \"commodityId\": 1}";
        JsonHandler.addToBuyList(jsonString , dataBase);
        assertEquals(dataBase.findCommodity(1), dataBase.findUser("user1").getBuyList().get(0));
    }

    @Test
    public void getCommoditiesByCategory(){

    }
}


