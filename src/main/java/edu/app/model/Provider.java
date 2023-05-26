package edu.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Provider {
    private long id;
    private String name;
    private LocalDate registryDate;
    private List<Commodity> commodities = new ArrayList<Commodity>();

    private String ImgUrl ;

    public Provider(long id, String name, LocalDate registryDate, String ImgUrl){
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
        this.ImgUrl = ImgUrl;
    }

    public void update(String name, LocalDate registryDate , String imgUrl) {
        this.name = name;
        this.registryDate = registryDate;
        this.ImgUrl = ImgUrl;
    }

    public double getAverageRate() {
        if (this.commodities.size() == 0)
            return 0D;
        double totalRate = 0;
        for (Commodity commodity : this.commodities)
            totalRate += commodity.getRating();
        return totalRate / (this.commodities.size());
    }

    public void addCommodity(Commodity commodity) {
        this.commodities.add(commodity);
    }

    public String toString () {
        return this.name + " -> " + this.id;
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {return ImgUrl; }

    public long getId() {
        return id;
    }

    public LocalDate getRegistryDate() {
        return registryDate;
    }

    public List<Commodity> getCommodities() {
        return commodities;
    }
}
