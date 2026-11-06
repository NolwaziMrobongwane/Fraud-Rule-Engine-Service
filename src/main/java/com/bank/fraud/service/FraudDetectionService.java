package com.bank.fraud.service;

import com.bank.fraud.domain.Transaction;
import com.bank.fraud.rules.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FraudDetectionService {
    private final List<FraudRule> rules = new ArrayList<>();

    public FraudDetectionService() {
        rules.add(new LargeAmountRule());
        rules.add(new OddHourRule());
        rules.add(new ForeignLocationRule());
        rules.add(new FrequencyRule());
    }

    public Transaction check(Transaction t) {
        for (FraudRule rule : rules) {
            if (rule.isFraud(t)) {
                t.setFlagged(true);
                t.setReason(t.getReason() +
                rule.getReason() + "; ");
            }
        }
        return t;
    }
}
