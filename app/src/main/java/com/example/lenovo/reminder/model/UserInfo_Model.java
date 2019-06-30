package com.example.lenovo.reminder.model;

public class UserInfo_Model {

    String wallet, limit, id, number;

    public UserInfo_Model(String wallet, String limit, String id, String number) {
        this.wallet = wallet;
        this.limit = limit;
        this.id = id;
        this.number = number;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
