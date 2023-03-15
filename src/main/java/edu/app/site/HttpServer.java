package edu.app.site;


import edu.app.api.Commodity;
import edu.app.api.DB;
import io.javalin.Javalin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpServer {
    Javalin app;
    DB database;
    static String baseSiteAddress = "src/pages/site/";

    public HttpServer() throws Exception{
        this.database = new DB();
        Initial.initDatabase(this.database);
        PageBuilder.createPages(this.database);
        this.app  = Javalin.create(/*config*/);

        addHandlers();
    }

    private void addHandlers() {
        app.get("/commodities", ctx -> {
           ctx.html(getFileContent(baseSiteAddress + "commodities.html"));
        });
        for (Commodity commodity : this.database.getCommodities()) {
            app.get("/commodities/" +commodity.getId(), ctx -> {
                ctx.html(getFileContent(baseSiteAddress + "commodity_"+commodity.getId()+".html"));
            });
        }
    }

    private static String getFileContent(String fileName) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(fileName));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "\"" + fileName + "\" file Not found!!!";
        }
    }


    public void startServer(int port) {
        this.app.start(port);
    }

    public static void main(String[] args) {

        /*Javalin app  = Javalin.create(*//*config*//*)
                .get("/", ctx -> ctx.result("Hello World"))
                .start(7070);*/
        //System.out.println(app.getClass().getName());
    }
}
