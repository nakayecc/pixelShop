package com.pixel.model;

public class Level {
    private String levelName;
    private int expReq;


    public Level(String levelName, int expReq) {
        this.levelName = levelName;
        this.expReq = expReq;
    }

    public Level() {
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getExpReq() {
        return expReq;
    }

    public void setExpReq(int expReq) {
        this.expReq = expReq;
    }
}
