package com.pixel.model;

public class Student extends User {

    private int mentor_id;
    private  int cass_id;

    public Student(int id, String name, String password, String roleName, int mentor_id, int cass_id) {
        super(id, name, password, roleName);
        this.mentor_id = mentor_id;
        this.cass_id = cass_id;
    }

    public Student(String name, String password, String roleName, int mentor_id, int cass_id) {
        super(name, password, roleName);
        this.mentor_id = mentor_id;
        this.cass_id = cass_id;
    }
    
    public Student(){

    }

    public int getMentor_id() {
        return mentor_id;
    }

    public void setMentor_id(int mentor_id) {
        this.mentor_id = mentor_id;
    }

    public int getCass_id() {
        return cass_id;
    }

    public void setCass_id(int cass_id) {
        this.cass_id = cass_id;
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getRoleName();
    }
}
