package com.bank.fraud.service;

import com.bank.fraud.domain.Transaction;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvTransactionReader {

    public List<Transaction> read(InputStream input) throws Exception {
        List<Transaction> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line;
        boolean firstLine = true;
        while ((line = reader.readLine()) != null) {
            if (firstLine) { firstLine = false; continue; } // skip header
            if (line.isBlank()) continue;
            String[] parts = line.split(",");
            Transaction t = new Transaction();
            t.setTransactionId(parts[0].trim());
            t.setCustomerId(parts[1].trim());
            t.setAmount(Double.parseDouble(parts[2].trim()));
            t.setLocation(parts[3].trim());
            t.setHour(Integer.parseInt(parts[4].trim()));
            t.setTransactionsInLastHour(Integer.parseInt(parts[5].trim()));
            list.add(t);
        }
        return list;
    }
}
