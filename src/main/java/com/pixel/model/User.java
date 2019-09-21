package com.pixel.model;

public abstract class User {
    private int id;
    private String name;
    private String password;
    private String roleName;

    public User(int id, String name, String password, String roleName) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.roleName = roleName;
    }

    public User(int id, String name, String roleName) {
        this.id = id;
        this.name = name;
        this.roleName = roleName;
    }

    public User(String name, String password, String roleName) {
        this.name = name;
        this.password = password;
        this.roleName = roleName;
    }

    public User() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}


