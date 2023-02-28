import edu.app.api.Commodity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RateTest {
    private Commodity commodity;


    @BeforeEach
    public void initTest() {
        commodity = new Commodity(10, "shoes", 5, 300,
                new ArrayList<>(Arrays.asList("ALI", "MAMAD")), 0, 0);
    }

    @Test
    public void testCommodityRate(){
        commodity.rate("matin" , 5);
        commodity.rate("ali" , 7);
        assertEquals(6,commodity.getRating());
    }

    @Test
    public void testDuplicateRate(){
        commodity.rate("ali" , 4);
        commodity.rate("majid" , 10);
        commodity.rate("matin" , 10);
        //till now the score is 8
        commodity.rate("ali" , 1);
        assertEquals(7,commodity.getRating());
        //now the score should be 7 because ali's score has changed
    }

}
