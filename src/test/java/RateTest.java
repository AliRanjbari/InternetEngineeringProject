import edu.app.api.Commodity;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RateTest {
    @Test
    public void testCommodityRate(){
        Commodity commodity = new Commodity(10,"shoes",5,300,new ArrayList<>(Arrays.asList("ALI" , "MAMAD")) , 0 ,0 );
        commodity.rate("matin" , 5);
        assertEquals(5,commodity.getRating());
    }
}
