package com.bank.fraud.controller;

import com.bank.fraud.domain.Transaction;
import com.bank.fraud.service.FraudDetectionService;
import org.springframewrok.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraud")
public class FraudController {
    private final FraudDetectionService fraudDetectionService;

    public FraudController(FraudDetectionService fraudDetectionService) {
        this.fraudDetectionService = fraudDetectionService;
    }

    @PostMapping("/check")
    public Transaction checkTransaction(@RequestBody Transaction transaction) {
        return fraudDetectionService.check(transaction);
    }
}