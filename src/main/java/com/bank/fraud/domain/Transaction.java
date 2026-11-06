package com.bank.fraud.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"transactionId", "customerId", "amount", "location", "hour", "transactionsInLastHour", "isFlagged", "reason"})
public class Transaction {
    private String transactionId;
    private String customerId;
    private double amount;
    private String location;
    private int hour;
    private int transactionsInLastHour;
    private boolean isFlagged = false;
    private String reason = "";

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

    public String getTransactionId() { return transactionId;}
    public void setTransactionId(String transactionId) { this.transactionId = transactionId;}

    public String getCustomerId() { return customerId;}
    public void setCustomerId(String customerId) { this.customerId = customerId;}

    public double getAmount() { return amount;}
    public void setAmount(double amount) { this.amount = amount;}

    public String getLocation() { return location;}
    public void setLocation(String location) { this.location = location;}

    public int getHour() { return hour;}
    public void setHour(int hour) { this.hour = hour;}

    public int getTransactionsInLastHour() { return transactionsInLastHour;}
    public void setTransactionsInLastHour(int transactionsInLastHour) { this.transactionsInLastHour = transactionsInLastHour;}

    @JsonProperty("isFlagged")
    public boolean isFlagged() { return isFlagged;}
    @JsonProperty("isFlagged")
    public void setFlagged(boolean flagged) { isFlagged = flagged;}

    public String getReason() { return reason;}
    public void setReason(String reason) { this.reason = reason;}
}