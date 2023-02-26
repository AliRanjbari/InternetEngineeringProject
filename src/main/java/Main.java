// import org.json.simple.JSONObject;
import edu.app.api.User;

import java.util.Scanner;

// import java.time.LocalDate;
import edu.app.api.Handler;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Handler handler = new Handler();
       while (true) {
           String input = in.nextLine();
           handler.getCommand(input);
       }
    }
}
// Kiram to matin
// change
