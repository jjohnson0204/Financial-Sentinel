package com.light1111.sentinel.domain.service;

import com.light1111.sentinel.domain.model.CoachingAdvice;
import com.light1111.sentinel.domain.model.Transaction;
import com.light1111.sentinel.domain.model.TransactionCategory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FinancialCoachService {

    public CoachingAdvice analyze(Transaction transaction) {
        BigDecimal amount = transaction.amount();
        TransactionCategory category = transaction.category();

        // 1. Handle Crypto specifically
        if (category == TransactionCategory.CRYPTO) {
            if (amount.compareTo(new BigDecimal("5000")) > 0) {
                return new CoachingAdvice("CRITICAL", "Extreme volatility alert! Massive crypto outflow detected.", "CRYPTO");
            }
            return new CoachingAdvice("WARNING", "Crypto purchase detected. Invest wisely.", "CRYPTO");
        }

        // 2. Handle Large Transport
        if (category == TransactionCategory.TRANSPORT && amount.compareTo(new BigDecimal("10000")) > 0) {
            return new CoachingAdvice("WARNING", "Significant vehicle-related expense.", "TRANSPORT");
        }

        // 3. Luxury check
        if (category == TransactionCategory.SHOPPING && amount.compareTo(new BigDecimal("1000")) > 0) {
            return new CoachingAdvice("WARNING", "Large luxury purchase detected.", "SHOPPING");
        }

        // 4. Default
        return new CoachingAdvice("STABLE", "Transaction fits normal spending patterns.", "GENERAL");
    }
}