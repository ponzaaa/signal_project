package com.data_management.records;

import java.util.*;

/**
 * Represents a patient and manages their medical records.
 * This class stores patient-specific data, allowing for the addition and
 * retrieval of medical records based on specified criteria.
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

    /**
     * Adds a record to the list of timestamped records
     * @param data the value of the condition that got recorded as a double
     * @param recordType the type of record whose value is stored
     * @param timestamp time component at which the value was recorded
     */
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

    /**
     * Getter method for the list of records of the {@link Patient}
     * @return HashMap that, given the condition/record type, returns
     * the list of timestamped values that had been recorded and stored
     */
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

    /**
     * Specific getter method that return a certain record filtered by its timestamp
     * @param condition condition for which we want to find the record
     * @param timestamp time component at which the value was recorded
     * @return the {@link PatientRecord} for that timestamp
     */
    public PatientRecord getRecordAtTime(String condition, long timestamp) {
        ArrayList<PatientRecord> records = patientRecords.get(condition);
        if (records != null) {
            for (PatientRecord record : records) {
                if (record.getTimestamp() == timestamp) {
                    return record;
                }
            }
        }
        return null;
    }

    public ArrayList<PatientRecord> getRecord(String condition) {
        return patientRecords.get(condition);
    }
}
