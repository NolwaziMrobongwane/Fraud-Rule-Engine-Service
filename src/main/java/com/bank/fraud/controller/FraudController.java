package com.bank.fraud.controller;

import com.bank.fraud.domain.Transaction;
import com.bank.fraud.dto.TransactionDto;
import com.bank.fraud.repository.TransactionRepository;
import com.bank.fraud.service.CsvTransactionReader;
import com.bank.fraud.service.FraudDetectionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/fraud")
public class FraudController {

    private final FraudDetectionService fraudDetectionService;
    private final CsvTransactionReader csvReader;
    private final TransactionRepository repository;

    public FraudController(FraudDetectionService fraudDetectionService, CsvTransactionReader csvReader, TransactionRepository repository) {
        this.fraudDetectionService = fraudDetectionService;
        this.csvReader = csvReader;
        this.repository = repository;
    }

    @PostMapping("/check")
    public Transaction checkTransaction(@Valid @RequestBody TransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(dto.transactionId);
        transaction.setCustomerId(dto.customerId);
        transaction.setAmount(dto.amount);
        transaction.setLocation(dto.location);
        transaction.setHour(dto.hour);
        transaction.setTransactionsInLastHour(dto.transactionsInLastHour);
        fraudDetectionService.check(transaction);
        return repository.save(transaction);
    }

    @PostMapping(value = "/check-batch", consumes = "multipart/form-data")
    public List<Transaction> checkBatch(@RequestParam("file") MultipartFile file) throws Exception {
        List<Transaction> txns = csvReader.read(file.getInputStream());
        for (Transaction t : txns) {
            fraudDetectionService.check(t);
            repository.save(t);
        }
        return txns;
    }

    @GetMapping("/all")
    public List<Transaction> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Transaction getById(@PathVariable String id) {
        return repository.findById(id).orElseThrow();
    }
}