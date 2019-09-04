package com.pixel.model;

public class Artifact {

    private int id;
    private String name;
    private  String description;
    private int price;
    private boolean is_global;

    public Artifact(int id, String name, String description, int price, boolean is_global) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.is_global = is_global;
    }

    public Artifact(String name, String description, int price, boolean is_global) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.is_global = is_global;
    }

    public Artifact() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isIs_global() {
        return is_global;
    }

    public void setIs_global(boolean is_global) {
        this.is_global = is_global;
    }
}
