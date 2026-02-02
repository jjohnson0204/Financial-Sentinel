package com.light1111.sentinel.web.dto;

import com.light1111.sentinel.domain.model.Transaction;
import com.light1111.sentinel.domain.service.AnomalyDetectionService;
import com.light1111.sentinel.domain.service.IngestionService;
import com.light1111.sentinel.infrastructure.kafka.TransactionProducer;
import com.light1111.sentinel.infrastructure.repository.TransactionEntity;
import com.light1111.sentinel.infrastructure.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final IngestionService ingestionService;
    private final TransactionRepository repository;
    private final AnomalyDetectionService anomalyService;
    private final TransactionProducer kafkaProducer;

    // Inject both the Service AND the Repository
    public TransactionController(
            IngestionService ingestionService,
            TransactionRepository repository,
            AnomalyDetectionService anomalyService,
            TransactionProducer kafkaProducer) {
        this.ingestionService = ingestionService;
        this.repository = repository;
        this.anomalyService = anomalyService;
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadTransactions(@RequestParam("file") MultipartFile file) {
        try {
            var records = ingestionService.process(file.getInputStream());

            for (Transaction record : records) {
                // 1. Analyze for Anomaly (Keep this for console logging/specific logic)
                if (anomalyService.isAnomaly(record)) {
                    System.out.println("⚠️ ANOMALY: " + record.description());
                }

                // 2. SEND TO KAFKA (Move it here to see EVERYTHING in the browser)
                kafkaProducer.sendTransaction(record);

                // 3. Save to DB
                repository.save(mapToEntity(record));
            }

            return ResponseEntity.ok("Processed " + records.size() + " transactions.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<List<UserSummary>> getSummary() {
        var topSpenders = repository.findTopSpenders().stream()
                .map(row -> new UserSummary((String) row[0], (java.math.BigDecimal) row[1]))
                .toList();
        return ResponseEntity.ok(topSpenders);
    }

    public record UserSummary(String userId, java.math.BigDecimal totalSpent) {}

    // This is the 'mapToEntity' method that was missing
    private TransactionEntity mapToEntity(Transaction record) {
        TransactionEntity entity = new TransactionEntity();
        entity.setId(record.id());
        entity.setUserId(record.userId());
        entity.setAmount(record.amount());
        entity.setDescription(record.description());
        entity.setCategory(record.category().name()); // Convert Enum to String for DB
        entity.setCreatedAt(record.createdAt());
        return entity;
    }
}