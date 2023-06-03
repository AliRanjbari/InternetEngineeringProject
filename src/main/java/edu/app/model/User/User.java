package edu.app.model.User;

import edu.app.model.Commodity.Commodity;
import edu.app.model.CommodityInBuyList.CommodityInBuyList;
import edu.app.model.Discount.Discount;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String password;
    private String email;
    private LocalDate birthDay;
    private String address;
    @ManyToMany
    @JoinTable(name="BUY_LIST",
                joinColumns = @JoinColumn(name="USER_ID"),
                inverseJoinColumns = @JoinColumn(name = "COMMODITY_ID"))
    private final List<Commodity> buyList = new ArrayList<Commodity>();
    @ElementCollection
    @CollectionTable(name="number_of_commodities_in_buy_list",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "commodity_id")
    @Column(name = "buy_list_quantity")
    private final  Map<Long , Integer> numberOfCommoditiesInBuyList = new HashMap<>();
    @ManyToMany
    @JoinTable(name="PURCHASED_LIST",
            joinColumns = @JoinColumn(name="USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "COMMODITY_ID"))
    private final List<CommodityInBuyList> purchasedList = new ArrayList<>();
    private long credit;
    @ManyToOne
    @JoinColumn(name="DISCOUNT_ID")
    private Discount currentDiscount;
    @ManyToMany
    @JoinTable(name = "USED_DISCOUNT",
                joinColumns = @JoinColumn(name = "USER_ID"),
                inverseJoinColumns = @JoinColumn(name = "DISCOUNT_ID"))
    private final List<Discount> usedDiscount = new ArrayList<Discount>();

    public User(String userName, String password, String email, LocalDate birthDay, String address, long credit) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.birthDay = birthDay;
        this.address = address;
        this.credit = credit;
    }

    public void update(String password, String email, LocalDate birthDay, String address, long credit) {
        this.password = password;
        this.email = email;
        this.birthDay = birthDay;
        this.address = address;
        this.credit = credit;
    }

    public void addItemToList (Commodity commodity) {
        this.buyList.add(commodity);
        for (HashMap.Entry <Long,Integer> item : this.numberOfCommoditiesInBuyList.entrySet())
        {
            if(item.getKey() == commodity.getId())
            {
                item.setValue(item.getValue()+1);
                return;
            }
        }
        this.numberOfCommoditiesInBuyList.put(commodity.getId(),1);
    }

    public void removeCommodity (long commodityId) {
        if (findItemId(commodityId) == -1)
            throw new RuntimeException("This commodity is not in the Buy List");

        int index = findItemId(commodityId);
        this.buyList.remove(index);
        for (HashMap.Entry <Long,Integer> item : this.numberOfCommoditiesInBuyList.entrySet())
        {
            if(item.getKey() == commodityId)
            {
                item.setValue(item.getValue()-1);
                return;
            }
        }
    }

    private int findItemId (long commodityId) {
        for (int i = 0; i < this.buyList.size(); i++)
            if (this.buyList.get(i).getId() == commodityId)
                return i;
        return -1;
    }

    public void purchaseBuyList() {
        long totalCost = getTotalBuyListPrice();

        if (totalCost <= this.credit) {
            this.credit = this.credit - totalCost;

            for (int i = 0; i < this.getBuyList().size() ;i++) {
                CommodityInBuyList temp = new CommodityInBuyList(this.getBuyList().get(i) , this.getNumberOfCommodityInBuyList(this.getBuyList().get(i).getId()));
                this.purchasedList.add(temp);
            }
//            this.purchasedList.addAll(this.buyList);
            this.buyList.clear();
            this.usedDiscount.add(this.currentDiscount);
            this.currentDiscount = null;
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

    public long getTotalBuyListPrice() {
        long totalPrice = 0;
        for(Commodity commodity : this.buyList)
            totalPrice += commodity.getPrice();

        if (this.currentDiscount != null)
            return (long)(totalPrice * this.currentDiscount.getRemainingPercentage());

        return totalPrice;
    }

    public List<CommodityInBuyList> getPurchasedList() {
        return purchasedList;
    }

    public long getCredit() {
        return credit;
    }

    public void addCredit(long credit) {
        this.credit += credit;
    }

    public boolean hasDiscount() {
        return this.currentDiscount != null;
    }

    public Discount getCurrentDiscount() {
        return currentDiscount;
    }

    public void setDiscount(Discount discount) {
        for (Discount d : this.usedDiscount)
            if (d.getDiscountCode().equals(discount.getDiscountCode()))
                throw new RuntimeException("Can not use this Discount again!");
        this.currentDiscount = discount;
    }

    public void removeDiscount() {
        this.currentDiscount = null;
    }

    public List<Discount> getUsedDiscount() { return usedDiscount; }

    public Map<Long, Integer> getNumberOfCommoditiesInBuyList() { return numberOfCommoditiesInBuyList; }

    public int getNumberOfCommodityInBuyList (long CommodityId) {

        int numberOfCommodityInBuyList = 0;
        for (int i = 0; i < this.buyList.size(); i++) {
            if (this.buyList.get(i).getId() == CommodityId)
                numberOfCommodityInBuyList++;
        }
        return numberOfCommodityInBuyList;
    }
}
