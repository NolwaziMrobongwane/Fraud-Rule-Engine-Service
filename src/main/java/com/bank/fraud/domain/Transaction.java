package com.bank.fraud.domain;

public class Transaction {
    public String transactionId;
    public String customerId;
    public double amount;
    public String location;
    public int hour;
    public boolean isFlagged = false;
    public String reason = "";

    public Transaction(String transactionId, String customerId, double amount, String location, int hour) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.amount = amount;
        this.location = location;
        this.hour = hour;
    }
}