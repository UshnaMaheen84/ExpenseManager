package com.example.lenovo.reminder.model;

public class LoanModel {

    String person,amount,returnDate,type,loan_id;
    String date,month,year;

    String onlyDate,onlyMonth,number;

    public LoanModel() {
    }

    public LoanModel(String person, String amount, String returnDate, String type, String loan_id, String date, String month, String year, String onlyDate, String onlyMonth, String number) {
        this.person = person;
        this.amount = amount;
        this.returnDate = returnDate;
        this.type = type;
        this.loan_id = loan_id;
        this.date = date;
        this.month = month;
        this.year = year;
        this.onlyDate = onlyDate;
        this.onlyMonth = onlyMonth;
        this.number = number;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
