package com.pixel.model;

public class SackInventory {

    private int id;
    private int userId;
    private int artifactId;
    private boolean ready;
    private int price;

    public SackInventory(int id, int userId, int artifactId, boolean ready, int price) {
        this.id = id;
        this.userId = userId;
        this.artifactId = artifactId;
        this.ready = ready;
        this.price = price;
    }

    public SackInventory(int userId, int artifactId, boolean ready, int price) {
        this.userId = userId;
        this.artifactId = artifactId;
        this.ready = ready;
        this.price = price;
    }

    public SackInventory() {
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

    public int getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(int artifactId) {
        this.artifactId = artifactId;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
