package com.light1111.sentinel.infrastructure.csv;

import com.light1111.sentinel.domain.exception.TransactionParsingException;
import com.light1111.sentinel.domain.model.Transaction;
import com.light1111.sentinel.domain.service.CategoryService; // Import the service
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Component
public class TransactionMapper {

    private final CategoryService categoryService;

    // Spring will automatically inject the CategoryService here
    public TransactionMapper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public Transaction toRecord(String[] csvLine) {
        try {
            String description = csvLine[2];
            return new Transaction(
                    UUID.randomUUID(),
                    csvLine[0], // userId
                    new BigDecimal(csvLine[1]), // amount
                    description,
                    categoryService.identifyCategory(description),
                    Instant.now()
            );
        } catch (Exception e) {
            throw new TransactionParsingException("Failed to map CSV line", e);
        }
    }
}