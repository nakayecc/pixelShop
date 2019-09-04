package com.pixel.model;

public class QuestCategory {

    private int id;
    private String name;

    public QuestCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public QuestCategory(String name) {
        this.name = name;
    }

    public QuestCategory() {
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
}
