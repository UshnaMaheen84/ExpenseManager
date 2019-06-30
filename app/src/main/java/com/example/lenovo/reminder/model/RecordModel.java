package com.example.lenovo.reminder.model;

public class RecordModel {
    String item,price,address,exp_id;
    String date,month,year;

    String onlyDate,onlyMonth,number;

    public RecordModel() {
    }

    public RecordModel(String item, String price, String address, String exp_id, String date, String month, String year, String onlyDate, String onlyMonth, String number) {
        this.item = item;
        this.price = price;
        this.address = address;
        this.exp_id = exp_id;
        this.date = date;
        this.month = month;
        this.year = year;
        this.onlyDate = onlyDate;
        this.onlyMonth = onlyMonth;
        this.number = number;
    }

    public String getExp_id() {
        return exp_id;
    }

    public void setExp_id(String exp_id) {
        this.exp_id = exp_id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getOnlyDate() {
        return onlyDate;
    }

    public void setOnlyDate(String onlyDate) {
        this.onlyDate = onlyDate;
    }

    public String getOnlyMonth() {
        return onlyMonth;
    }

    public void setOnlyMonth(String onlyMonth) {
        this.onlyMonth = onlyMonth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
