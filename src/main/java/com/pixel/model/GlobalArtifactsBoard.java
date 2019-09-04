package com.pixel.model;

public class GlobalArtifactsBoard {
    private int id;
    private int priceGathered;
    private int priceTotal;
    private Artifact artifact;

    public GlobalArtifactsBoard(int id, int priceGathered, int priceTotal, Artifact artifact) {
        this.id = id;
        this.priceGathered = priceGathered;
        this.priceTotal = priceTotal;
        this.artifact = artifact;
    }

    public GlobalArtifactsBoard(int priceGathered, int priceTotal, Artifact artifact) {
        this.priceGathered = priceGathered;
        this.priceTotal = priceTotal;
        this.artifact = artifact;
    }

    public GlobalArtifactsBoard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriceGathered() {
        return priceGathered;
    }

    public void setPriceGathered(int priceGathered) {
        this.priceGathered = priceGathered;
    }

    public int getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(int priceTotal) {
        this.priceTotal = priceTotal;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }
}
