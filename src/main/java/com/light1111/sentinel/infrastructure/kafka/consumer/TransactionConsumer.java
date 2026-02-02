package com.light1111.sentinel.infrastructure.kafka.consumer;

import com.light1111.sentinel.infrastructure.kafka.dto.TransactionDTO;
import com.light1111.sentinel.domain.service.FinancialCoachService;
import com.light1111.sentinel.domain.model.CoachingAdvice;
import com.light1111.sentinel.domain.model.Transaction;
import com.light1111.sentinel.domain.model.TransactionCategory;
import com.light1111.sentinel.web.dto.AlertController;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component // Use Component for Infrastructure Listeners
public class TransactionConsumer {

    private final FinancialCoachService coachService;
    private final AlertController alertController;

    public TransactionConsumer(FinancialCoachService coachService, AlertController alertController) {
        this.coachService = coachService;
        this.alertController = alertController;
    }

    @KafkaListener(topics = "transactions-raw", groupId = "sentinel-group")
    public void consume(TransactionDTO dto) {
        // 1. Log the arrival
        System.out.println("📥 RECEIVED FROM KAFKA: " + dto.getDescription());

        // 2. Standard Alert Logic
        System.out.println("🚨 REAL-TIME ALERT: Suspicious activity detected for User: " + dto.getUserId());
        System.out.println("Details: " + dto.getDescription() + " | Amount: $" + dto.getAmount());

        // 3. Convert DTO to Domain for the Coach
        Transaction transaction = new Transaction(
                UUID.fromString(dto.getId()),
                dto.getUserId(),
                dto.getAmount(),
                dto.getDescription(),
                TransactionCategory.valueOf(dto.getCategory()),
                dto.getCreatedAt()
        );

        // 4. Trigger the AI Coach Service
        CoachingAdvice advice = coachService.analyze(transaction);

        // PUSH TO BROWSER
        alertController.sendAlert(new AlertController.LiveAlert(
                dto.getDescription(),
                dto.getAmount(),
                advice.riskLevel(),
                advice.recommendation(),
                advice.categoryFocus()
        ));

        System.out.println("🤖 AI COACH ADVICE:");
        System.out.println("   [Risk Level: " + advice.riskLevel() + "]");
        System.out.println("   > " + advice.recommendation());
        System.out.println("---------------------------------------");
    }
}