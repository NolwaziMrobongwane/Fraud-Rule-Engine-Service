package com.bank.fraud.dto;

import jakarta.validation.constraints.*;

public class TransactionDto {

    @NotBlank
    public String transactionId;

    @NotBlank
    public String customerId;

    @Min(0)
    public double amount;

    @NotBlank
    public String location;

    @Min(0) @Max(23)
    public int hour;

    @Min(0)
    public int transactionsInLastHour;
}
