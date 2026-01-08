package com.example.progettoispw.bean;

import com.example.progettoispw.model.UserType;

public class UserBean {

    private String username;
    private String password;
    private UserType usertype;

    public UserBean() {}

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }

    public UserType getUsertype() {
        return usertype;
    }
}
