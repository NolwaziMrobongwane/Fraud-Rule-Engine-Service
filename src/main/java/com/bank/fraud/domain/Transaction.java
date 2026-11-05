package com.bank.fraud.domain;

public class Transaction {
    public String transactionId;
    public String customerId;
    public double amount;
    public String location;
    public int hour;
    public int transactionsInLastHour;
    public boolean isFlagged = false;
    public String reason = "";

    public Transaction() {
    }

    public Transaction(String transactionId, String customerId, double amount, String location, int hour, int transactionsInLastHour) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.amount = amount;
        this.location = location;
        this.hour = hour;
        this.transactionsInLastHour = transactionsInLastHour;
    }
}