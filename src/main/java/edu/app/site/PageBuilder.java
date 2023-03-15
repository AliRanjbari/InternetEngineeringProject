package edu.app.site;

import edu.app.api.DB;
import edu.app.api.User;
import edu.app.api.Commodity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageBuilder {

    static String baseTemplateAddress = "src/pages/template/";
    static String baseSiteAddress = "src/pages/site/";

    static void createPages(DB database) throws Exception {
        clearSiteFolder();
        createCommoditiesPage(database.getCommodities());
    }

    private static void clearSiteFolder() {
        File file = new File(baseSiteAddress);
        File filesList[] = file.listFiles();
        for(File f : filesList) {
            if(f.isFile())
                f.delete();
        }
    }

    private static void createCommoditiesPage(List<Commodity> commodities)  throws Exception {
        File input = new File(baseTemplateAddress + "Commodities.html");
        Document doc = Jsoup.parse(input, "UTF-8");
        Element table = doc.select("table").first();
        for (Commodity commodity : commodities) {
            Element newRow = table.appendElement("tr");
            newRow.appendElement("td").text(String.valueOf(commodity.getId()));
            newRow.appendElement("td").text(commodity.getName());
            newRow.appendElement("td").text(String.valueOf(commodity.getProviderId()));
            newRow.appendElement("td").text(String.valueOf(commodity.getPrice()));
            newRow.appendElement("td").text(commodity.getCategories().toString());
            newRow.appendElement("td").text(String.valueOf(commodity.getRating()));
            newRow.appendElement("td").text(String.valueOf(commodity.getInStock()));
            Element link = newRow.appendElement("td").appendElement("a");
            link.text("Link");
            link.attr("href", "/commodities/" + commodity.getId());
            createCommodityPage(commodity);
        }
        File output = new File(baseSiteAddress + "commodities.html");
        FileWriter writer = new FileWriter(output);
        doc.outputSettings().prettyPrint(true);
        writer.write(doc.outerHtml());
        writer.close();
    }

    private static void createCommodityPage(Commodity commodity) throws Exception{
        File input = new File(baseTemplateAddress + "Commodity.html");
        Document doc = Jsoup.parse(input, "UTF-8");
        Element ul = doc.getElementsByTag("ul").get(0);

        ul.getElementById("id").text("Id: " + commodity.getId());
        ul.getElementById("name").text("Name: " + commodity.getName());
        ul.getElementById("providerId").text("Provider Id: " + commodity.getProviderId());
        ul.getElementById("price").text("Price: " + commodity.getPrice());
        ul.getElementById("categories").text("Categories :" + commodity.getCategories());
        ul.getElementById("rating").text("Rating :" + commodity.getRating());
        ul.getElementById("inStock").text("In Stock :" + commodity.getInStock());

        File output = new File(baseSiteAddress + "commodity_"+commodity.getId()+".html");
        FileWriter writer = new FileWriter(output);
        doc.outputSettings().prettyPrint(true);
        writer.write(doc.outerHtml());
        writer.close();
    }

    public static void main(String[] args) throws Exception{
       /* ArrayList<String> cats = new ArrayList<String>(Arrays.asList("tech", "mobile"));
        Commodity commodity = new Commodity(10, "phone", 20, 30000, cats, 3.2, 1000);
        createCommodityPage(commodity);*/
        clearSiteFolder();

    }

}
