package com.data_management.records;

public class DiastolicPressureRecord implements PatientRecord {

    private int patientID;
    private double data;
    private long timestamp;

    public DiastolicPressureRecord(int patientID, double data, long timestamp) {
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
        return "Diastolic Pressure";
    }

    @Override
    public String toString(){
        return "Patient ID: " + patientID + ", Diastolic Pressure: " + data + ", Timestamp: " + timestamp;
    }
}
