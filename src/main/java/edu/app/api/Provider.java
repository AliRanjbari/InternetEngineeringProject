package edu.app.api;

import java.time.LocalDate;

public class Provider {
    private long id;
    private String name;
    private LocalDate registryDate;

    public Provider(long id, String name, LocalDate registryDate){
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
    }

    public void update(String name, LocalDate registryDate) {
        this.name = name;
        this.registryDate = registryDate;
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
}
