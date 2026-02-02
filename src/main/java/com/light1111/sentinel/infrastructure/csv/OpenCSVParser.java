package com.light1111.sentinel.infrastructure.csv;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class OpenCSVParser {

    public List<String[]> parseContents(InputStream is) throws Exception {
        // Skip the header row (user_id, amount, description)
        try (CSVReader reader = new CSVReader(new InputStreamReader(is))) {
            List<String[]> allData = reader.readAll();
            if (!allData.isEmpty()) {
                allData.remove(0);
            }
            return allData;
        }
    }
}