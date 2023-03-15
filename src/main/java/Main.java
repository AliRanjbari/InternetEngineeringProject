import edu.app.api.DB;

import edu.app.site.HttpServer;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer httpServer = new HttpServer();
        httpServer.startServer(7070);
    }
}
