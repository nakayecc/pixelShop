package com.pixel.model;

public class SackInventory {

    private int id;
    private int sackId;
    private int artifactId;
    private boolean ready;
    private int price;

    public SackInventory(int id, int sackId, int artifactId, boolean ready, int price) {
        this.id = id;
        this.sackId = sackId;
        this.artifactId = artifactId;
        this.ready = ready;
        this.price = price;
    }

    public SackInventory(int sackId, int artifactId, boolean ready, int price) {
        this.sackId = sackId;
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

    public int getSackId() {
        return sackId;
    }

    public void setSackId(int sackId) {
        this.sackId = sackId;
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
