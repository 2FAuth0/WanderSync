package com.example.wandersync.model;

public class User {
    private String name;
    private String email;
    private String password;
    private String id;

    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setID(String id) {
        this.id = id;
    }
}
