package edu.app.site;

import edu.app.api.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class PageBuilder {

    static String baseTemplateAddress = "src/pages/template/";
    static String baseSiteAddress = "src/pages/site/";

    static void createPages(DB database) throws Exception {
        clearSiteFolder();
        createCommoditiesPage(database.getCommodities());

        for (Provider provider : database.getProviders())
            createProviderPage(provider);

        for (User user : database.getUsers())
            createUserPage(user);
    }

    private static void clearSiteFolder() {
        File file = new File(baseSiteAddress);
        File filesList[] = file.listFiles();
        for(File f : filesList) {
            if(f.isFile() &&  !f.getName().equals(".gitignore"))
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

        // fill commodity information
        Element ul = doc.getElementsByTag("ul").get(0);
        ul.getElementById("id").text("Id: " + commodity.getId());
        ul.getElementById("name").text("Name: " + commodity.getName());
        ul.getElementById("providerId").text("Provider Id: " + commodity.getProviderId());
        ul.getElementById("price").text("Price: " + commodity.getPrice());
        ul.getElementById("categories").text("Categories :" + commodity.getCategories());
        ul.getElementById("rating").text("Rating :" + commodity.getRating());
        ul.getElementById("inStock").text("In Stock :" + commodity.getInStock());

        // fill comments
        Element table = doc.select("table").first();
        for (Comment comment : commodity.getCommentList()) {
            Element newRow = table.appendElement("tr");
            newRow.appendElement("td").text(comment.getEmail());
            newRow.appendElement("td").text(comment.getText());
            newRow.appendElement("td").text(comment.getCommentDate().toString());

        }

        File output = new File(baseSiteAddress + "commodity_"+commodity.getId()+".html");
        FileWriter writer = new FileWriter(output);
        doc.outputSettings().prettyPrint(true);
        writer.write(doc.outerHtml());
        writer.close();
    }

    private static void createProviderPage(Provider provider) throws Exception {
        File input = new File(baseTemplateAddress + "Provider.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        // change ul block
        Element ul = doc.select("ul").first();
        ul.getElementById("id").text("Id: " + provider.getId());
        ul.getElementById("name").text("Name: " + provider.getName());
        ul.getElementById("registryDate").text("Registry Date: " + provider.getRegistryDate().toString());

        // add commodities
        Element table = doc.select("table").first();
        for (Commodity commodity : provider.getCommodities()) {
            Element newRow = table.appendElement("tr");
            newRow.appendElement("td").text(String.valueOf(commodity.getId()));
            newRow.appendElement("td").text(commodity.getName());
            newRow.appendElement("td").text(String.valueOf(commodity.getPrice()));
            newRow.appendElement("td").text(commodity.getCategories().toString());
            newRow.appendElement("td").text(String.valueOf(commodity.getRating()));
            newRow.appendElement("td").text(String.valueOf(commodity.getInStock()));
            Element link = newRow.appendElement("td").appendElement("a");
            link.text("Link");
            link.attr("href", "/commodities/" + commodity.getId());
        }

        File output = new File(baseSiteAddress + "provider_"+provider.getId()+".html");
        FileWriter writer = new FileWriter(output);
        doc.outputSettings().prettyPrint(true);
        writer.write(doc.outerHtml());
        writer.close();
    }

    private static void createUserPage(User user) throws Exception {
        File input = new File(baseTemplateAddress + "User.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        // change ul block
        Element ul = doc.select("ul").first();
        ul.getElementById("username").text("Username: " + user.getUserName());
        ul.getElementById("email").text("Email: " + user.getEmail());
        ul.getElementById("birthDate").text("Birth Date: " + user.getBirthDay().toString());
        ul.getElementById("address").text(user.getAddress());
        ul.getElementById("credit").text("Credit: " + user.getCredit());

        // add buyList
        Element table = doc.select("table").first();
        for (Commodity commodity : user.getBuyList()) {
            Element newRow = table.appendElement("tr");
            newRow.appendElement("td").text(String.valueOf(commodity.getId()));
            newRow.appendElement("td").text(commodity.getName());
            newRow.appendElement("td").text(String.valueOf(commodity.getProviderId()));
            newRow.appendElement("td").text(String.valueOf(commodity.getPrice()));
            newRow.appendElement("td").text(commodity.getCategories().toString());
            newRow.appendElement("td").text(String.valueOf(commodity.getRating()));
            newRow.appendElement("td").text(String.valueOf(commodity.getInStock()));
            // link
            Element link = newRow.appendElement("td").appendElement("a");
            link.text("Link");
            link.attr("href", "/commodities/" + commodity.getId());
            // button
            Element removeLink = newRow.appendElement("td").appendElement("a");
            link.text("Remove");
            link.attr("href", "/removeFromBuyList/" + user.getUserName() + "/" + commodity.getId());
        }

        // add purchasedList
        Element table2 = doc.select("table").get(1);
        for (Commodity commodity : user.getPurchasedList()) {
            Element newRow = table2.appendElement("tr");
            newRow.appendElement("td").text(String.valueOf(commodity.getId()));
            newRow.appendElement("td").text(commodity.getName());
            newRow.appendElement("td").text(String.valueOf(commodity.getProviderId()));
            newRow.appendElement("td").text(String.valueOf(commodity.getPrice()));
            newRow.appendElement("td").text(commodity.getCategories().toString());
            newRow.appendElement("td").text(String.valueOf(commodity.getRating()));
            newRow.appendElement("td").text(String.valueOf(commodity.getInStock()));
            // link
            Element link = newRow.appendElement("td").appendElement("a");
            link.text("Link");
            link.attr("href", "/commodities/" + commodity.getId());
        }

        File output = new File(baseSiteAddress + "user_"+user.getUserName()+".html");
        FileWriter writer = new FileWriter(output);
        doc.outputSettings().prettyPrint(true);
        writer.write(doc.outerHtml());
        writer.close();
    }


    public static String createSearchCommodityPage(List<Commodity> commodities) throws Exception{             //returns html
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
        doc.outputSettings().prettyPrint(true);
        return doc.outerHtml();
    }

    public static void main(String[] args) throws Exception{
       /* ArrayList<String> cats = new ArrayList<String>(Arrays.asList("tech", "mobile"));
        Commodity commodity = new Commodity(10, "phone", 20, 30000, cats, 3.2, 1000);
        createCommodityPage(commodity);*/
        clearSiteFolder();

    }

}
