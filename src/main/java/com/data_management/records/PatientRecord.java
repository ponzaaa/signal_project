package com.data_management.records;

/**
 * Represents a single record of patient data at a specific point in time.
 * This class stores all necessary details for a single observation or
 * measurement
 * taken from a patient, including the type of record (such as ECG, blood
 * pressure),
 * the measurement value, and the exact timestamp when the measurement was
 * taken.
 */
public interface PatientRecord {

    /**
     * Returns the patient ID associated with this record.
     * 
     * @return the patient ID
     */
    int getPatientId();

    /**
     * Returns the measurement value of this record.
     * 
     * @return the measurement value
     */
    double getMeasurementValue();

    /**
     * Returns the timestamp when this record was taken.
     * 
     * @return the timestamp in milliseconds since epoch
     */
    long getTimestamp();

    /**
     * Returns the type of record (e.g., "ECG", "Blood Pressure").
     * 
     * @return the record type
     */
    String getRecordType();

}
