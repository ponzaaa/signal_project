package com.data_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DataFileReader implements DataReader{

    private List<String> filePaths;
    public DataFileReader(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        for (String filePath : filePaths) {
            File file = new File(filePath);
            if (file.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        parseAndStore(line, dataStorage);
                    }
                } catch (IOException e) {
                    System.err.println("Error reading file " + filePath + ": " + e.getMessage());
                }
            } else {
                System.err.println("File not found: " + filePath);
            }
        }
    }

    private void parseAndStore(String line, DataStorage dataStorage) {
        // Parse the line
        String[] parts = line.split(", ");
        int patientId = Integer.parseInt(parts[0].split(": ")[1]);
        long timestamp = Long.parseLong(parts[1].split(": ")[1]);
        String label = parts[2].split(": ")[1];
        String dataStr = parts[3].split(": ")[1];

        // Remove percentage sign if presentL
        if (dataStr.endsWith("%")) {
            dataStr = dataStr.substring(0, dataStr.length() - 1);
        }

        double data = Double.parseDouble(dataStr);

        // Add the data to the storage
        dataStorage.addPatientData(patientId, data, label, timestamp);
    }
}
