package com.pixel.model;

public class Creep extends User {
    public Creep(int id, String name, String password, String roleName) {
        super(id, name, password, roleName);
    }

    public Creep(String name, String password, String roleName) {
        super(name, password, roleName);
    }

    public Creep() {
    }
}
