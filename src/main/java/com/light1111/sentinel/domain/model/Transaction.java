package com.light1111.sentinel.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record Transaction(
        UUID id,
        @NotBlank String userId,
        @NotBlank BigDecimal amount,
        String description,
        TransactionCategory category,
        Instant createdAt
        ) {

    @JsonCreator
    public Transaction(
            @JsonProperty("id") UUID id,
            @JsonProperty("userId") String userId,
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("description") String description,
            @JsonProperty("category") TransactionCategory category,
            @JsonProperty("createdAt") Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.createdAt = createdAt;
        
        // Objects.requireNonNull checks
        Objects.requireNonNull(id, "Transaction ID cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");

        // Fintech logic: Check for zero-amount noise
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Transaction amount cannot be zero");
        }
    }

    // Helper method for AI risk assessment
    public boolean isHighValue() {
        return amount.compareTo(new BigDecimal("1000.00")) > 0;
    }
}
