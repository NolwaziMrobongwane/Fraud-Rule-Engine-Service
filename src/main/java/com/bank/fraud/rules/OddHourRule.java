package com.bank.fraud.rules;

import com.bank.fraud.domain.Transaction;

public class OddHourRule implements FraudRule {
    @Override 
    public boolean isFraud(Transaction t) {
        //suspicious if the transaction happens between 12am and 4am
        return t.getHour() >= 0 && t.getHour() <= 4;
    }
    @Override
    public String getReason() {
        return "Transaction at odd hours (12am-4am)";
    }
}