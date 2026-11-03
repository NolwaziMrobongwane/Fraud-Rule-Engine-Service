package com.bank.fraud.rules;

import com.bank.fraud.domain.Transaction;

public class ForeignLocationRule implements FraudRule{
    @Override
    public boolean isFraud(Transaction t){
        //Flag if transaction is not in South Africa
        return !t.location.equalsIgnoreCase("South Africa") && !t.location.equalsIgnoreCase("SA");
    }
    @Override
    public String getReason() {
        return "Transaction from foreign location";
    }
}
