package com.pixel.model;

public class StudentsClass {
    private int id;
    private String name;

    public StudentsClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public StudentsClass(String name) {
        this.name = name;
    }

    public StudentsClass() {
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
