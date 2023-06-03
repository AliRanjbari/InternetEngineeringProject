package edu.app.model.Provider;

import edu.app.model.Commodity.Commodity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Provider {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    private LocalDate registryDate;
    @OneToMany(mappedBy = "provider")
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
