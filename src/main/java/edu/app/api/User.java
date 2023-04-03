package edu.app.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class User {
    private String userName;
    private String password;
    private String email;
    private LocalDate birthDay;
    private String address;
    private List<Commodity> buyList = new ArrayList<Commodity>();
    private List<Commodity> purchasedList = new ArrayList<Commodity>();
    private long credit;

    public User(String userName, String password, String email, LocalDate birthDay, String address, long credit) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.birthDay = birthDay;
        this.address = address;
        this.credit = credit;
    }

    void update(String password, String email, LocalDate birthDay, String address, long credit) {
        this.password = password;
        this.email = email;
        this.birthDay = birthDay;
        this.address = address;
        this.credit = credit;
    }

    public void addItemToList (Commodity commodity) {
        this.buyList.add(commodity);
    }

    public void removeCommodity (long commodityId) {
        if (findItemId(commodityId) == -1)
            throw new RuntimeException("This commodity is not in the Buy List");

        int index = findItemId(commodityId);
        this.buyList.remove(index);
    }

    private int findItemId (long commodityId) {
        for (int i = 0; i < this.buyList.size(); i++)
            if (this.buyList.get(i).getId() == commodityId)
                return i;
        return -1;
    }

    void purchaseBuyList() {
        long totalCost = 0;
        for (Commodity c : this.buyList)
            totalCost += c.getPrice();

        if (totalCost <= this.credit) {
            this.credit = this.credit - totalCost;
            for (Commodity c : this.buyList)
                this.purchasedList.add(c);
            this.buyList.clear();
        }
        else
            throw new RuntimeException("Not Enough credit");
    }

    public static boolean isValidUsername(String username) {
        return !username.matches(".*[!@#].*");
    }

    public String toString () {
        return this.userName + " -> " + this.password;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public String getAddress() {
        return address;
    }

    public List<Commodity> getBuyList() {
        return buyList;
    }

    public List<Commodity> getPurchasedList() {
        return purchasedList;
    }

    public long getCredit() {
        return credit;
    }

    public void addCredit(long credit) {
        this.credit += credit;
    }

}
