package com.data_management.records;

/**
 * Represents a single unit of the record for Blood Saturation data
 */
public class SaturationRecord implements PatientRecord {

    private final int patientID;
    private final double data;
    private final long timestamp;

    public SaturationRecord(int patientID, double data, long timestamp) {
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
        return "Saturation";
    }

    @Override
    public String toString(){
        return "Patient ID: " + patientID + ", Saturation: " + data + ", Timestamp: " + timestamp;
    }
}
