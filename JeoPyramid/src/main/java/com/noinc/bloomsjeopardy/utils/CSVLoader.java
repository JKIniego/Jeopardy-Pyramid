package com.noinc.bloomsjeopardy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVLoader {
    
    public static List<String[]> loadCSV(String filePath) {
        List<String[]> records = new ArrayList<>();
        
        try (InputStream is = CSVLoader.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("#", -1);
                records.add(parts);
            }
            
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading CSV file: " + filePath);
            e.printStackTrace();
        }
        
        return records;
    }
    
    public static String[][] loadCSVAsArray(String filePath) {
        List<String[]> records = loadCSV(filePath);
        return records.toArray(new String[0][]);
    }
}