package com.light1111.sentinel.infrastructure.kafka;

import com.light1111.sentinel.domain.model.Transaction;
import com.light1111.sentinel.infrastructure.kafka.dto.TransactionDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransactionProducer {

    private final KafkaTemplate<String, TransactionDTO> kafkaTemplate;

    public TransactionProducer(KafkaTemplate<String, TransactionDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransaction(Transaction transaction) {
        // Convert domain model to DTO for Kafka serialization
        TransactionDTO dto = TransactionDTO.from(transaction);
        kafkaTemplate.send("transactions-raw", transaction.userId(), dto);
    }
}