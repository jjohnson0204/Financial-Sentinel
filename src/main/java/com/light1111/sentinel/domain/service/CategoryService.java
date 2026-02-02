package com.light1111.sentinel.domain.service;

import com.light1111.sentinel.domain.model.TransactionCategory;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    public TransactionCategory identifyCategory(String description) {
        if (description == null) return TransactionCategory.UNCATEGORIZED;
        String desc = description.toLowerCase();

        return switch (desc) {
            case String s when s.contains("starbucks") || s.contains("coffee") -> TransactionCategory.FOOD_DRINK;
            case String s when s.contains("jewelry") || s.contains("apple") -> TransactionCategory.SHOPPING;
            case String s when s.contains("crypto") || s.contains("bitcoin") -> TransactionCategory.CRYPTO;
            case String s when s.contains("uber") || s.contains("car") -> TransactionCategory.TRANSPORT;
            default -> TransactionCategory.UNCATEGORIZED;
        };
    }
}