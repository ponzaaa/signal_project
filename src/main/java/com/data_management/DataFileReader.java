package com.data_management;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Class implementing DataReader implementation, for parsing the data
 * from a File and store it in the given DataStorage object
 */
public class DataFileReader implements DataReader{

    private List<String> filePaths;

    /**
     * @param filePaths directory of the file from which data
     *                  has to be parsed
     */
    public DataFileReader(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    /**
     * public method that triggers the process of reading the data from
     * the given file path
     * @param dataStorage the storage where data will be stored
     * @throws IOException if the file is not found
     */
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

    /**
     * Parses data from the file, feeds it into the dataStorage, which
     * created teh PatientRecord object for that patient
     * @param line String representation of one full line of text
     * @param dataStorage storage where the record is going to be stored
     */
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
