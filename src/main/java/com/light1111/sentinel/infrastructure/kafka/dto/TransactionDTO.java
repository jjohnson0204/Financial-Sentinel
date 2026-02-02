package com.light1111.sentinel.infrastructure.kafka.dto;

import com.light1111.sentinel.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionDTO {
    private String id;
    private String userId;
    private BigDecimal amount;
    private String description;
    private String category;
    private Instant createdAt;

    // Default constructor for Jackson
    public TransactionDTO() {
    }

    public TransactionDTO(String id, String userId, BigDecimal amount, String description, String category, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.createdAt = createdAt;
    }

    // Factory method to create from domain Transaction
    public static TransactionDTO from(Transaction transaction) {
        return new TransactionDTO(
                transaction.id().toString(),
                transaction.userId(),
                transaction.amount(),
                transaction.description(),
                transaction.category().name(),
                transaction.createdAt()
        );
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
