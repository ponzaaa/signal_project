package com.data_management.records;

public class SystolicPressureRecord implements PatientRecord {

    private int patientID;
    private double data;
    private long timestamp;

    public SystolicPressureRecord(int patientID, double data, long timestamp) {
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
        return "Systolic Pressure";
    }

    @Override
    public String toString(){
        return "Patient ID: " + patientID + ", Systolic Pressure: " + data + ", Timestamp: " + timestamp;
    }
}
