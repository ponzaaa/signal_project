package com.data_management.records;

import java.util.*;

/**
 * Represents a patient and manages their medical records.
 * This class stores patient-specific data, allowing for the addition and
 * retrieval
 * of medical records based on specified criteria.
 */
public class Patient {
    private int patientId;
    private final Map<String, ArrayList<PatientRecord>> patientRecords;

    /**
     * Constructs a new Patient with a specified ID.
     * Initializes an HashMap mapping to each one of the
     * patient records.
     *
     * @param patientId the unique identifier for the patient
     */
    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new HashMap<>();
        patientRecords.put("Cholesterol", new ArrayList<>());
        patientRecords.put("Saturation", new ArrayList<>());
        patientRecords.put("Diastolic Pressure", new ArrayList<>());
        patientRecords.put("Systolic Pressure", new ArrayList<>());
        patientRecords.put("ECG", new ArrayList<>());
        patientRecords.put("Red Blood Cells", new ArrayList<>());
        patientRecords.put("White Blood Cells", new ArrayList<>());
    }


    public void addRecord(double data, String recordType, long timestamp) {
        switch (recordType){
            case "Cholesterol":
                patientRecords.get(recordType).add(new CholesterolRecord(patientId, data, timestamp));
                break;
            case "Saturation":
                patientRecords.get(recordType).add(new SaturationRecord(patientId, data, timestamp));
                break;
            case "Diastolic Pressure":
                patientRecords.get(recordType).add(new DiastolicPressureRecord(patientId, data, timestamp));
                break;
            case "Systolic Pressure":
                patientRecords.get(recordType).add(new SystolicPressureRecord(patientId, data, timestamp));
                break;
            case "ECG":
                patientRecords.get(recordType).add(new ECGRecord(patientId, data, timestamp));
                break;
            case "Red Blood Cells":
                patientRecords.get(recordType).add(new RedBloodCellsRecord(patientId, data, timestamp));
                break;
            case "White Blood Cells":
                patientRecords.get(recordType).add(new WhiteBloodCellsRecord(patientId, data, timestamp));
                break;
        }
    }

    public Map<String, ArrayList<PatientRecord>> getPatientRecords() {
        return patientRecords;
    }

    /**
     * getter method for patient unique number identifier
     * @return patient's ID number
     */
    public int getId() {
        return patientId;
    }
}
