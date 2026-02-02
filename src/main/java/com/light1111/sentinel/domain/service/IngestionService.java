package com.light1111.sentinel.domain.service;

import com.light1111.sentinel.domain.model.Transaction;
import com.light1111.sentinel.infrastructure.csv.OpenCSVParser; // New
import com.light1111.sentinel.infrastructure.csv.TransactionMapper;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class IngestionService {

    private final TransactionMapper mapper;
    private final OpenCSVParser csvParser; // Inject the parser

    public IngestionService(TransactionMapper mapper, OpenCSVParser csvParser) {
        this.mapper = mapper;
        this.csvParser = csvParser;
    }

    public List<Transaction> process(InputStream inputStream) {
        try {
            // 1. Use your OpenCSVParser instead of BufferedReader
            List<String[]> lines = csvParser.parseContents(inputStream);

            // 2. Map the String arrays into Transaction records
            return lines.stream()
                    .map(mapper::toRecord)
                    .toList();

        } catch (Exception e) {
            // It's better to catch the general Exception from OpenCSV here
            throw new RuntimeException("Error parsing CSV file with OpenCSV", e);
        }
    }
}