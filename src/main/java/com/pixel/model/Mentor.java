package com.pixel.model;

public class Mentor extends User {
    private int class_id;

    public Mentor(int id, String name, String password, String roleName, int class_id) {
        super(id, name, password, roleName);
        this.class_id = class_id;
    }

    public Mentor(int id, String name, String roleName, int class_id) {
        super(id, name, roleName);
        this.class_id = class_id;
    }

    public Mentor(String name, String password, String roleName, int class_id) {
        super(name, password, roleName);
        this.class_id = class_id;
    }

    public Mentor(){

    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }
}
