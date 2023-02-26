package edu.app.api;

import java.time.LocalDate;

public class Provider {
    long id;
    String name;
    LocalDate registryDate;

    public Provider(long id, String name, LocalDate registryDate){
        this.id = id;
        this.name = name;
        this.registryDate = registryDate;
    }
}
