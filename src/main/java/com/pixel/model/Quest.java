package com.pixel.model;

import java.util.Objects;

public class Quest {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quest quest = (Quest) o;
        return id == quest.id &&
                exp == quest.exp &&
                categoryId == quest.categoryId &&
                name.equals(quest.name) &&
                description.equals(quest.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, exp, categoryId);
    }

    private int id;
    private String name;
    private String description;
    private int exp;
    private int categoryId;


    public Quest(int id, String name, String description, int exp, int categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.exp = exp;
        this.categoryId = categoryId;
    }

    public Quest(String name, String description, int exp, int categoryId) {
        this.name = name;
        this.description = description;
        this.exp = exp;
        this.categoryId = categoryId;
    }

    public Quest() {
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

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String toString(){
        return getName()+ " "+getExp();
    }

    public static void main(String[] args) {
        Quest q1 = new Quest();
        Quest q2 = new Quest();
        System.out.println(q1.hashCode());
        System.out.println(q2.hashCode());
    }
}
