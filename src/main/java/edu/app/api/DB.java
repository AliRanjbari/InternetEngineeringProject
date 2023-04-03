package edu.app.api;

import com.sun.source.tree.LiteralTree;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DB {
    List<User> users;
    List<Commodity> commodities;
    List<Provider> providers;
    List<Comment> comments;
    List<Discount> discounts;

    public DB() {
        this.users = new ArrayList<User>();
        this.commodities = new ArrayList<Commodity>();
        this.providers = new ArrayList<Provider>();
        this.comments = new ArrayList<Comment>();
        this.discounts = new ArrayList<Discount>();
    }

    public void addUser(String userName, String password, String email, LocalDate birthDay, String address, long credit) {
        if(User.isValidUsername(userName) == false)
            throw new RuntimeException("Wrong username format");

        if (findUser(userName) != null){
            User user = findUser(userName);
            user.update(password, email, birthDay, address, credit);
        } else {
            User newUser = new User(userName, password, email, birthDay, address, credit);
            users.add(newUser);
        }
    }


    public void addUser(User user){
        if (findUser(user.getUserName()) != null){
            User newUser = findUser(user.getUserName());
            newUser.update(user.getPassword(), user.getEmail(),
                            user.getBirthDay(), user.getAddress(), user.getCredit());
        } else {
            User newUser = new User(user.getUserName(), user.getPassword(),
                                    user.getEmail(), user.getBirthDay(), user.getAddress(),
                                    user.getCredit());
            users.add(newUser);
        }
    }
    public void addProvider(Provider provider){
        if(findProvider(provider.getId()) != null) {
            Provider newProvider = findProvider(provider.getId());
            newProvider.update(provider.getName(), provider.getRegistryDate());
        } else {
            Provider newProvider = new Provider(provider.getId(), provider.getName(), provider.getRegistryDate());
            this.providers.add(newProvider);
        }
    }
    public void addProvider(long id, String name, LocalDate registryDate) {
        if(findProvider(id) != null) {
            Provider provider = findProvider(id);
            provider.update(name, registryDate);
        } else {
            Provider newProvider = new Provider(id, name, registryDate);
            this.providers.add(newProvider);
        }
    }

    public void addCommodity(long id, String name, long providerId, long price, ArrayList<String> categories, double rating, long inStock) {
        if(findProvider(providerId) == null)
            throw new RuntimeException("Provider does not exists");

        if(findCommodity(id) != null) {
            Commodity commodity = findCommodity(id);
            commodity.update(name, providerId, price, categories, rating, inStock);
        } else {
            Commodity newCommodity = new Commodity(id, name, providerId, price, categories, rating, inStock);
            this.commodities.add(newCommodity);
            Provider provider = findProvider(providerId);
            provider.addCommodity(newCommodity);
        }
    }

    public void addDiscount(String discountCode, long discount){
        Discount newDiscount = new Discount(discountCode , discount);
        this.discounts.add(newDiscount);
    }


    public void addComment(String email, long commodityId, String text, LocalDate commentDate){

        if(findCommodity(commodityId) == null)
            throw new RuntimeException("Commodity does not exists");



        Comment comment = new Comment(this.comments.size(), email, text, commentDate);

        Commodity commodity = findCommodity(commodityId);

        this.comments.add(comment);
        commodity.addComment(comment);

    }

    public void addCommodity(Commodity inputCommodity){
        if(findProvider(inputCommodity.getProviderId()) == null)
            throw new RuntimeException("Provider does not exists");

        if(findCommodity(inputCommodity.getId()) != null) {
            Commodity newCommodity = findCommodity(inputCommodity.getId());
            newCommodity.update(inputCommodity.getName(), inputCommodity.getProviderId(),
                                inputCommodity.getPrice(), inputCommodity.getCategories(),
                                inputCommodity.getRating(), inputCommodity.getInStock());
        } else {
            Commodity newCommodity = new Commodity(inputCommodity.getId(), inputCommodity.getName(),
                                                    inputCommodity.getProviderId(), inputCommodity.getPrice(),
                                                    inputCommodity.getCategories(), inputCommodity.getRating(),
                                                    inputCommodity.getInStock());
            this.commodities.add(newCommodity);
        }
    }


    public void addCredit(String username, long credit) {
        if(findUser(username) == null)
            throw new RuntimeException("User not found");
        User user = findUser(username);
        user.addCredit(credit);
    }

    public void rateCommodity(String username, long commodityId, double score) {
        if(findUser(username) == null)
            throw new RuntimeException("User not found");
        if(findCommodity(commodityId) == null)
            throw new RuntimeException("Commodity not found");

        Commodity commodity = findCommodity(commodityId);
        commodity.rate(username, score);
    }

    public void addToBuyList(String username, long commodityId) {
        if (findUser(username) == null)
            throw new RuntimeException("User not found");
        if (findCommodity(commodityId) == null)
            throw new RuntimeException("Commodity not found");

        User user = findUser(username);
        Commodity commodity = findCommodity(commodityId);
        user.addItemToList(commodity);
    }

    public void removeFromBuyList(String username, long commodityId) {
        if (findUser(username) == null)
            throw new RuntimeException("User not found");
        if (findCommodity(commodityId) == null)
            throw new RuntimeException("Commodity not found");

        User user = findUser(username);
        user.removeCommodity(commodityId);
    }

    public List<Commodity> getCommoditiesByCategory(String categoryName) {
        List<Commodity> listCommodityByCategory = new ArrayList<Commodity>();
        for (Commodity commodity : this.commodities)
            if (commodity.doesCategoryExists(categoryName))
                listCommodityByCategory.add(commodity);

        return listCommodityByCategory;
    }

    public void voteComment(String username, int commentId, long vote) {
        if (findUser(username) == null)
            throw new RuntimeException("User not found");
        if (this.comments.size() <= commentId)                  // id of comment is the position in the comment list
            throw new RuntimeException("Comment not found");

        this.comments.get(commentId).rate(username, vote);
    }

    public void purchaseBuyList(String username){
        if (findUser(username) == null)
            throw new RuntimeException("User not found");
        User user = findUser(username);
        user.purchaseBuyList();
    }

    public List<Commodity> getBuyList(String username) {
        if (findUser(username) == null)
            throw new RuntimeException("User not found");

        User user = findUser(username);

        return  user.getBuyList();
    }

    public User findUser(String userName) {
        for (User user : this.users)
            if (userName.equals(user.getUserName()))
                return user;
        return null;
    }

    public Provider findProvider(long id) {
        for (Provider provider : this.providers)
            if(provider.getId() == id)
                return provider;
        return null;
    }

    public Commodity findCommodity(long id) {
        for (Commodity commodity : this.commodities)
            if (commodity.getId() == id)
                return commodity;
        return null;
    }

    public List<Commodity> findCommodityByPrice(long startPrice , long endPrice){
        List<Commodity> commoditiesInRange = new ArrayList<Commodity>();
        for (Commodity commodity : this.commodities) {
            if (commodity.getPrice() >= startPrice && commodity.getPrice() <= endPrice)
                commoditiesInRange.add(commodity);
        }
        return commoditiesInRange;
    }
    public List<User> getUsers() {
        return users;
    }

    public List<Provider> getProviders() {
        return providers;
    }

    public List<Commodity> getCommodities() {
        return this.commodities;
    }
    public List<Comment> getComments(){
        return this.comments;
    }
 }

