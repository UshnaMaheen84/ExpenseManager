package com.example.lenovo.reminder.model;

public class UserModel {

    String name,email,phone,password, wallet, limit;

    public UserModel() {
    }

    public UserModel(String name, String email, String phone, String password, String wallet, String limit) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.wallet = wallet;
        this.limit = limit;
    }

    public UserModel(String wallet, String limit) {
        this.wallet = wallet;
        this.limit= limit;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
