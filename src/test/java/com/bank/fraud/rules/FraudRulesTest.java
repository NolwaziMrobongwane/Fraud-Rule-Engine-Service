package com.bank.fraud.rules;

import com.bank.fraud.domain.Transaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FraudRulesTest {

    private Transaction txn(double amount, String location, int hour, int freq) {
        Transaction t = new Transaction();
        t.setTransactionId("TXN-TEST");
        t.setCustomerId("CUST-001");
        t.setAmount(amount);
        t.setLocation(location);
        t.setHour(hour);
        t.setTransactionsInLastHour(freq);
        return t;
    }

    @Test
    void largeAmount_flagsOver20000() {
        assertTrue(new LargeAmountRule().isFraud(txn(25000, "SA", 10, 1)));
        assertFalse(new LargeAmountRule().isFraud(txn(5000, "SA", 10, 1)));
    }

    @Test
    void oddHour_flags0to4() {
        assertTrue(new OddHourRule().isFraud(txn(5000, "SA", 2, 1)));
        assertFalse(new OddHourRule().isFraud(txn(5000, "SA", 10, 1)));
    }

    @Test
    void foreignLocation_flagsOutsideSA() {
        assertTrue(new ForeignLocationRule().isFraud(txn(5000, "USA", 10, 1)));
        assertFalse(new ForeignLocationRule().isFraud(txn(5000, "SA", 10, 1)));
    }

    @Test
    void frequency_flagsOver3() {
        assertTrue(new FrequencyRule().isFraud(txn(5000, "SA", 10, 5)));
        assertFalse(new FrequencyRule().isFraud(txn(5000, "SA", 10, 1)));
    }
}
