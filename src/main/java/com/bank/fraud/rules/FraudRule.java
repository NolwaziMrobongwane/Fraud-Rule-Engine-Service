package main.java.com.bank.fraud.rules;

import main.java.com.bank.fraud.domain.Transaction;

public interface FraudRule{
    boolean isFraud(Transaction t);
    String getReason();
}