package edu.app.site;


import edu.app.api.Commodity;
import edu.app.api.DB;
import edu.app.api.Provider;
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
        String status200Path = "src/pages/template/200.html";
        app.error(403, ctx -> {
            ctx.html(getFileContent("src/pages/template/403.html"));
        });
        app.error(404, ctx -> {
            ctx.html(getFileContent("src/pages/template/404.html"));
        });

        app.get("/commodities", ctx -> {
           ctx.html(getFileContent(baseSiteAddress + "commodities.html"));
        });

        app.get("/commodities/{id}", ctx -> {
            String path = baseSiteAddress + "commodity_"+ctx.pathParam("id")+".html";
            if (doesFileExists(path))
                ctx.html(getFileContent(path));
            else
                ctx.status(404);
        });

        app.get("/providers/{id}", ctx -> {
            String path = baseSiteAddress + "provider_"+ctx.pathParam("id")+".html";
            if (doesFileExists(path))
                ctx.html(getFileContent(path));
            else
                ctx.status(404);
        });

        app.get("/user/{username}", ctx -> {
            String path = baseSiteAddress + "user_"+ctx.pathParam("username")+".html";
            if (doesFileExists(path))
                ctx.html(getFileContent(path));
            else
                ctx.status(404);
        });

        app.get("/addCredit/{username}/{credit}", ctx -> {
            try {
                this.database.addCredit(ctx.pathParam("username"),
                        Long.parseLong(ctx.pathParam("credit")));
                ctx.html(getFileContent(status200Path));
            } catch (Exception e) {
                ctx.status(403);
            }
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

    static boolean doesFileExists(String path) {
        File f = new File(path);
        if(f.exists() && !f.isDirectory())
            return true;
        return false;
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