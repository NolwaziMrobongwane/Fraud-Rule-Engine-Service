package com.bank.fraud.rules;

import com.bank.fraud.domain.Transaction;

public class LargeAmountRule implements FraudRule {
    @Override
    public boolean isFraud(Transaction t) {
        return t.getAmount() > 20000;
    }
    @Override
    public String getReason() {
        return "Amount exceeds R20 000 limit";
    }
}