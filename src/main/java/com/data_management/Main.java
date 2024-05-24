package com.data_management;

import com.alerts.AlertGenerator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {

    /**
     * The main method for the DataStorage class.
     * Initializes the system, reads data into storage, and continuously monitors
     * and evaluates patient data.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) throws IOException {
        List<String> pathsToGeneratedData = Arrays.asList("/Users/giovanniponzini/Desktop/output/Cholesterol.txt",
                "/Users/giovanniponzini/Desktop/output/DiastolicPressure.txt",
                "/Users/giovanniponzini/Desktop/output/ECG.txt",
                "/Users/giovanniponzini/Desktop/output/RedBloodCells.txt",
                "/Users/giovanniponzini/Desktop/output/Saturation.txt",
                "/Users/giovanniponzini/Desktop/output/SystolicPressure.txt",
                "/Users/giovanniponzini/Desktop/output/WhiteBloodCells.txt");
        DataStorage storage = new DataStorage();
        DataReader reader = new DataFileReader(pathsToGeneratedData);

        // Assuming the reader has been properly initialized and can read data into the
        // storage
        reader.readData(storage);

        // Example of using DataStorage to retrieve and print records for a patient
        List<PatientRecord> records = storage.getRecords(1, 1700000000000L, 1800000000000L);
        for (PatientRecord record : records) {
            System.out.println("Record for Patient ID: " + record.getPatientId() +
                    ", Type: " + record.getRecordType() +
                    ", Data: " + record.getMeasurementValue() +
                    ", Timestamp: " + record.getTimestamp());
        }

        // Initialize the AlertGenerator with the storage
        AlertGenerator alertGenerator = new AlertGenerator(storage);

        // Evaluate all patients' data to check for conditions that may trigger alerts
        for (Patient patient : storage.getAllPatients()) {
            alertGenerator.evaluateData(patient, 1700000000000L, 1800000000000L);
        }
    }
}
