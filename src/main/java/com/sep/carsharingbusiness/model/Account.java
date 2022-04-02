package com.sep.carsharingbusiness.model;

public class Account {
    private String username;
    private String password;
    public Customer customer;

    public Account(String username, String password, Customer customer) {
        this.username = username;
        this.password = password;
        this.customer = customer;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.customer = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
