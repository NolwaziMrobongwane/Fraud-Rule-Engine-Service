package com.bank.fraud.rules;

import com.bank.fraud.domain.Transaction;

public interface FraudRule{
    boolean isFraud(Transaction t);
    String getReason();
}