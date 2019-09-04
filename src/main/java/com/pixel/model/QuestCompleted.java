package com.pixel.model;

public class QuestCompleted {

    private int userID;
    private int questID;
    private int expGained;


    public QuestCompleted(int userID, int questID, int expGained) {
        this.userID = userID;
        this.questID = questID;
        this.expGained = expGained;
    }

    public QuestCompleted(int questID, int expGained) {
        this.questID = questID;
        this.expGained = expGained;
    }

    public QuestCompleted() {
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getQuestID() {
        return questID;
    }

    public void setQuestID(int questID) {
        this.questID = questID;
    }

    public int getExpGained() {
        return expGained;
    }

    public void setExpGained(int expGained) {
        this.expGained = expGained;
    }
}
