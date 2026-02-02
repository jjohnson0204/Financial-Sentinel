package com.light1111.sentinel.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {
    // Aggregates total amount per user and returns the top 3
    @Query(value = "SELECT user_id, SUM(amount) as total FROM transactions " +
            "GROUP BY user_id ORDER BY total DESC LIMIT 3",
            nativeQuery = true)
    List<Object[]> findTopSpenders();
}
