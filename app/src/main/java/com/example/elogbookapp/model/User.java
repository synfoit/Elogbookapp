package com.example.elogbookapp.model;

public class User {
    int id;
    String name;
    String email;
    String role;
    String password;
    String userToken;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    boolean isDeleted;
    String phoneNumber;

    public User(int id,
                String name,
                String email,
                String role,
                String password,
                String userToken,
                boolean isDeleted,
                String phoneNumber)
    {this. id=id;
            this. name=name;
            this. email=email;
            this. role=role;
            this. password=password;
            this. userToken=userToken;
            this. isDeleted=isDeleted;
            this. phoneNumber=phoneNumber;

    }
}
