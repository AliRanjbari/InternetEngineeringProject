package edu.app.repository;

import edu.app.model.*;
import edu.app.model.Commodity.Commodity;
import edu.app.model.User.User;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.min;



public class DB {


    List<User> users;
    List<Commodity> commodities;
    List<Provider> providers;
    List<Comment> comments;
    List<Discount> discounts;
    SessionFactory sessionFactory;

    public DB() {
       /* sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Provider.class)
                .addAnnotatedClass(Discount.class)
                .addAnnotatedClass(CommodityInBuyList.class)
                .addAnnotatedClass(Commodity.class)
                .addAnnotatedClass(Comment.class)
                .buildSessionFactory();*/
        this.users = new ArrayList<User>();
        this.commodities = new ArrayList<Commodity>();
        this.providers = new ArrayList<Provider>();
        this.comments = new ArrayList<Comment>();
        this.discounts = new ArrayList<Discount>();
    }

    public void addUser(String userName, String password, String email, LocalDate birthDay, String address, long credit) {


        /*User newUser = new User(userName, password, email, birthDay, address, credit);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("baloot");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(newUser);
        entityManager.getTransaction().commit();*/


        /*try (Session session = sessionFactory.getCurrentSession()) {
            System.out.println("seesssion factory!");
            session.beginTransaction();
            User newUser = new User(userName, password, email, birthDay, address, credit);
            session.save(newUser);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("sessoin factory except");
            //
        }*/



        if(!User.isValidUsername(userName))
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
            newProvider.update(provider.getName(), provider.getRegistryDate(), provider.getImgUrl());
        } else {
            Provider newProvider = new Provider(provider.getId(), provider.getName(), provider.getRegistryDate(), provider.getImgUrl());
            this.providers.add(newProvider);
        }
    }
    public void addProvider(long id, String name, LocalDate registryDate, String ImgUrl) {
        if(findProvider(id) != null) {
            Provider provider = findProvider(id);
            provider.update(name, registryDate, ImgUrl);
        } else {
            Provider newProvider = new Provider(id, name, registryDate, ImgUrl);
            this.providers.add(newProvider);
        }
    }

    public void addCommodity(long id, String name, long providerId,
                             long price, ArrayList<String> categories, double rating, long inStock, String imgURL) {
        if(findProvider(providerId) == null)
            throw new RuntimeException("Provider does not exists");

        if(findCommodity(id) != null) {
            Commodity commodity = findCommodity(id);
            commodity.update(name, providerId, price, categories, rating, inStock, imgURL);
        } else {
            Commodity newCommodity = new Commodity(id, name, providerId, price, categories, rating, inStock, imgURL);
            this.commodities.add(newCommodity);
            Provider provider = findProvider(providerId);
            provider.addCommodity(newCommodity);
        }
    }

    public void addDiscount(String discountCode, long discount){
        /*try (Session session = sessionFactory.getCurrentSession()) {
            System.out.println("seesssion factory!");
            session.beginTransaction();
            Discount newDiscount = new Discount(discountCode , discount);
            session.persist(newDiscount);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("sessoin factory except");
            //
        }*/
        Discount newDiscount = new Discount(discountCode , discount);
        this.discounts.add(newDiscount);
    }


    public void addComment(String email, long commodityId, String text, LocalDate commentDate){

        if(findCommodity(commodityId) == null)
            throw new RuntimeException("Commodity does not exists");
        if(findUserByEmail(email) == null)
            throw new RuntimeException("User does not exists");

        User user = findUserByEmail(email);
        Comment comment = new Comment(this.comments.size(), user.getUserName(), text, commentDate);

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
                                inputCommodity.getRating(), inputCommodity.getInStock(), inputCommodity.getImgURL());
        } else {
            Commodity newCommodity = new Commodity(inputCommodity.getId(), inputCommodity.getName(),
                                                    inputCommodity.getProviderId(), inputCommodity.getPrice(),
                                                    inputCommodity.getCategories(), inputCommodity.getRating(),
                                                    inputCommodity.getInStock(), inputCommodity.getImgURL());
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
    public List<Commodity> getCommoditiesByName(String name) {
        List<Commodity> listCommodityByName = new ArrayList<Commodity>();
        for (Commodity commodity : this.commodities)
            if (commodity.getName().toLowerCase().contains(name.toLowerCase()))
                listCommodityByName.add(commodity);

        return listCommodityByName;
    }
    public List<Commodity> getCommoditiesByCategoryAndList(String categoryName , List<Commodity> commodities) {
        List<Commodity> listCommodityByCategory = new ArrayList<Commodity>();
        for (Commodity commodity : commodities)
            if (commodity.doesCategoryExists(categoryName))
                listCommodityByCategory.add(commodity);

        return listCommodityByCategory;
    }
    public List<Commodity> getCommoditiesByNameAndList(String name , List<Commodity> commodities) {
        List<Commodity> listCommodityByName = new ArrayList<Commodity>();
        for (Commodity commodity : commodities)
            if (commodity.getName().toLowerCase().contains(name.toLowerCase()))
                listCommodityByName.add(commodity);

        return listCommodityByName;
    }
    public List<Commodity> getCommoditiesByProviderAndList(String ProviderName , List<Commodity> commodities) {
        List<Commodity> listCommodityByProvider = new ArrayList<Commodity>();
        for (Commodity commodity : commodities)
            if (findProvider(commodity.getProviderId()).getName().toLowerCase().contains(ProviderName.toLowerCase()))
                listCommodityByProvider.add(commodity);

        return listCommodityByProvider;
    }

    public List<Commodity> getMostSimilarCommodities(Commodity commodity) {
        List<Commodity> mostSimilarCommodities = new ArrayList<>(this.commodities);
        mostSimilarCommodities.sort(Comparator.comparingDouble(c -> c.getRating() + 11 * commodity.hasSameCategories(c)));
        Collections.reverse(mostSimilarCommodities);
        return mostSimilarCommodities;
    }

    public List<Commodity> getCommoditiesSortByPrice() {
        List<Commodity> commoditiesSortedByPrice = new ArrayList<>(this.commodities);
        commoditiesSortedByPrice.sort(Comparator.comparingLong(Commodity::getPrice));
        Collections.reverse(commoditiesSortedByPrice);
        return commoditiesSortedByPrice;
    }
    public List<Commodity> getCommoditiesSortByPrice(List<Commodity> commodities) {
        List<Commodity> commoditiesSortedByPrice = new ArrayList<>(commodities);
        commoditiesSortedByPrice.sort(Comparator.comparingLong(Commodity::getPrice));
        Collections.reverse(commoditiesSortedByPrice);
        return commoditiesSortedByPrice;
    }
    public List<Commodity> getCommoditiesSortByName (List<Commodity> commodities) {
        List<Commodity> commoditiesSortedByName = new ArrayList<>(commodities);
        commoditiesSortedByName.sort(Comparator.comparing(Commodity::getName));
        return commoditiesSortedByName;
    }

    public void voteComment(String username, int commentId, long vote) {
        if (findUser(username) == null)
            throw new RuntimeException("User not found");
        if (this.comments.size() <= commentId)                  // id of comment is the position in the comment list
            throw new RuntimeException("Comment not found");

        this.comments.get(commentId).rate(username, vote);
    }

    public List<Commodity> getAvailableCommodities(){
        List<Commodity> availableCommodities= new ArrayList<Commodity>();
        for (int i = 0; i <this.commodities.size() ; i++){
            if (this.commodities.get(i).getInStock() > 0)
                availableCommodities.add(this.commodities.get(i));
        }
        return availableCommodities;
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

    public User findUserByEmail(String email) {
        for (User user : this.users)
            if (user.getEmail().equals(email))
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

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public Discount findDiscount(String discountCode) {
        for (Discount discount : this.discounts)
            if (discount.getDiscountCode().equals(discountCode))
                return discount;
        return null;
    }

    public void useDiscount(User user, String discountCode) {
        if (findDiscount(discountCode) == null)
            throw new RuntimeException("Discount not found");
        user.setDiscount(findDiscount(discountCode));
    }

    public List<Commodity> getPage (int PageNum , List<Commodity> commodities){
        List<Commodity> AvailableCommoditiesPage = commodities.subList((PageNum - 1) * 12 ,  min(((PageNum)* 12 ) , commodities.size()));
        return AvailableCommoditiesPage;
    }

}

