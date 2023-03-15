package edu.app.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Provider {
    private long id;
    private String name;
    private LocalDate registryDate;
    private List<Commodity> commodities = new ArrayList<Commodity>();

    public Provider(long id, String name, LocalDate registryDate){
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
    }

    public void update(String name, LocalDate registryDate) {
        this.name = name;
        this.registryDate = registryDate;
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
