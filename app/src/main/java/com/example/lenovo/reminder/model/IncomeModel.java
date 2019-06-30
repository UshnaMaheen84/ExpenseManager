package com.example.lenovo.reminder.model;

public class IncomeModel {

    String source,amount,income_id;
    String date,month,year;
    String onlyDate,onlyMonth,number;

    public IncomeModel() {
    }

    public IncomeModel(String source, String amount, String income_id, String date, String month, String year, String onlyDate, String onlyMonth, String number) {
        this.source = source;
        this.amount = amount;
        this.income_id = income_id;
        this.date = date;
        this.month = month;
        this.year = year;
        this.onlyDate = onlyDate;
        this.onlyMonth = onlyMonth;
        this.number = number;
    }

    public String getIncome_id() {
        return income_id;
    }

    public void setIncome_id(String income_id) {
        this.income_id = income_id;
    }

    public String getOnlyDate() {
        return onlyDate;
    }

    public void setOnlyDate(String onlyDate) {
        this.onlyDate = onlyDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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

    public String getOnlyMonth() {
        return onlyMonth;
    }

    public void setOnlyMonth(String onlyMonth) {
        this.onlyMonth = onlyMonth;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
