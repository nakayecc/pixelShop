package com.pixel.model;

public class Sack {

    private int id;
    private int userId;

    public Sack(int id, int userId) {
        this.id = id;
        this.userId = userId;
    }

    public Sack(int userId) {
        this.userId = userId;
    }

    public Sack() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
