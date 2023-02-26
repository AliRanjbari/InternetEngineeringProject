// import org.json.simple.JSONObject;
import edu.app.api.User;

import java.util.Scanner;

// import java.time.LocalDate;
import edu.app.api.Handler;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        Scanner in = new Scanner(System.in);
        Handler handler = new Handler();
       while (true) {
           String input = in.nextLine();
           handler.handleCommand(input);
       }
    }
}
