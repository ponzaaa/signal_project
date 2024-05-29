package com.data_management.records;

public class RedBloodCellsRecord implements PatientRecord {

    private int patientID;
    private double data;
    private long timestamp;

    public RedBloodCellsRecord(int patientID, double data, long timestamp) {
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
        return "Red Blood Cells";
    }

    @Override
    public String toString(){
        return "Patient ID: " + patientID + ", Red Blood Cells: " + data + ", Timestamp: " + timestamp;
    }
}
