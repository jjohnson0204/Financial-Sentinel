package com.light1111.sentinel.domain.service;

import ai.djl.inference.Predictor;
import com.light1111.sentinel.domain.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AnomalyDetectionService {

    // We can eventually load a .pt (PyTorch) model here
    private Predictor<Transaction, Boolean> predictor;

    public boolean isAnomaly(Transaction transaction) {
        // 1. Keep your rule-based "safety net"
        if (transaction.amount().compareTo(new BigDecimal("5000")) > 0) return true;

        // 2. Placeholder for DJL Inference
        // In Phase 2 expansion, we would call: predictor.predict(transaction)
        return performStatisticalCheck(transaction);
    }

    private boolean performStatisticalCheck(Transaction t) {
        // Mocking a probability score from an AI model
        double riskScore = Math.random();
        return riskScore > 0.95; // 5% chance of being flagged as a "statistical anomaly"
    }
}