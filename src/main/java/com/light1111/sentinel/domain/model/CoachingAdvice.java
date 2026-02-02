package com.light1111.sentinel.domain.model;

public record CoachingAdvice(
        String riskLevel,
        String recommendation,
        String categoryFocus
) {}