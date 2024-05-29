package com.data_management.records;

public class CholesterolRecord implements PatientRecord {

    private int patientID;
    private double data;
    private long timestamp;

    public CholesterolRecord(int patientID, double data, long timestamp) {
        this.patientID = patientID;
        this.data = data;
        this.timestamp = timestamp;
    }

    @Override
    public int getPatientId() {
        return patientID;
    }

    @Override
    public double getMeasurementValue() {
        return data;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getRecordType() {
        return "Cholesterol";
    }

    @Override
    public String toString(){
        return "Patient ID: " + patientID + ", Cholesterol: " + data + ", Timestamp: " + timestamp;
    }
}
