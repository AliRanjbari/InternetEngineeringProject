package edu.app.site;


import edu.app.api.DB;
import io.javalin.Javalin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HttpServer {
    Javalin app;
    DB database;

    public HttpServer() throws Exception{
        this.database = new DB();
        Initial.initDatabase(this.database);
        this.app  = Javalin.create(/*config*/)
                .get("/", ctx -> ctx.result("Hello World"));

        app.get("/index", ctx -> {
           ctx.html(getFileContent("src/pages/index.html"));
        });
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
