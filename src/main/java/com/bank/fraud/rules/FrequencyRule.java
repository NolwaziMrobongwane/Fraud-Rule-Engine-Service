package com.bank.fraud.rules;

import com.bank.fraud.domain.Transaction;

public class FrequencyRule implements FraudRule{
    @Override
    public boolean isFraud(Transaction t){
        // If client did more than 3 transactions in the last hour = suspicious frequency
        // Capitec would flag this as potential card testing / attempt to deplete client funds
        return t.getTransactionsInLastHour() > 3;
    }
    @Override
    public String getReason() {
        return "High frequency: more than 3 transactions in last hour";
    }
}
