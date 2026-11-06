package com.bank.fraud.controller;

import com.bank.fraud.domain.Transaction;
import com.bank.fraud.service.CsvTransactionReader;
import com.bank.fraud.service.FraudDetectionService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/fraud")
public class FraudController {

    private final FraudDetectionService fraudDetectionService;
    private final CsvTransactionReader csvReader;

    public FraudController(FraudDetectionService fraudDetectionService, CsvTransactionReader csvReader) {
        this.fraudDetectionService = fraudDetectionService;
        this.csvReader = csvReader;
    }

    @PostMapping("/check")
    public Transaction checkTransaction(@RequestBody Transaction transaction) {
        return fraudDetectionService.check(transaction);
    }

    @PostMapping(value = "/check-batch", consumes = "multipart/form-data")
    public List<Transaction> checkBatch(@RequestParam("file") MultipartFile file) throws Exception {
        List<Transaction> txns = csvReader.read(file.getInputStream());
        for (Transaction t : txns) {
            fraudDetectionService.check(t);
        }
        return txns;
    }
}