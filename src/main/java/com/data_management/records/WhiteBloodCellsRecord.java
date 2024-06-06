package com.data_management.records;

/**
 * Represents a single unit of the record for White Blood Cells data
 */
public class WhiteBloodCellsRecord implements PatientRecord {

    private final int patientID;
    private final double data;
    private final long timestamp;

    public WhiteBloodCellsRecord(int patientID, double data, long timestamp) {
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
        return "White Blood Cells";
    }

    @Override
    public String toString(){
        return "Patient ID: " + patientID + ", White Blood Cells: " + data + ", Timestamp: " + timestamp;
    }
}
