package com.data_management;

import com.alerts.*;
import com.alerts.objects.Alert;
import com.data_management.records.Patient;

import java.util.*;

/**
 * Manages storage and retrieval of patient data within a healthcare monitoring
 * system.
 * This class serves as a repository for all patient records, organized by
 * patient IDs.
 */
public class DataStorage {
    private static DataStorage dataStorage;
    private static Map<Integer, Alert> alerts = new HashMap<>();
    private final Map<Integer, Patient> patientMap; // Stores patient objects indexed by their unique patient ID.

    /**
     * Constructs a new instance of DataStorage, initializing the underlying storage
     * structure.
     */
    private DataStorage() {
        this.patientMap = new HashMap<>();
    }

    /**
     * Adds or updates patient data in the storage.
     * If the patient does not exist, a new Patient object is created and added to
     * the storage. Also runs an alert generator to check if there's something wrong.
     * Otherwise, the new data is added to the existing patient's records.
     *
     * @param patientId        the unique identifier of the patient
     * @param data the value of the health metric being recorded
     * @param recordType       the type of record, e.g., "HeartRate",
     *                         "BloodPressure"
     * @param timestamp        the time at which the measurement was taken, in
     *                         milliseconds since the Unix epoch
     */
    public void addPatientData(int patientId, double data, String recordType, long timestamp) {
        Patient patient = patientMap.get(patientId);
        if (patient == null) {
            patient = new Patient(patientId);
            patientMap.put(patientId, patient);
        }
        patient.addRecord(data, recordType, timestamp);

        // check for alert
        switch (recordType) {
            case "Saturation":
                checkForAlert(new BloodSaturationStrategy(), patientId, data, timestamp, patientMap);
                break;
            case "ECG":
                checkForAlert(new HeartRateStrategy(), patientId, data, timestamp, patientMap);
                break;
            case "Diastolic Pressure":
                checkForAlert(new DiastolicPressureStrategy(), patientId, data, timestamp, patientMap);
                break;
            case "Systolic Pressure":
                checkForAlert(new SystolicPressureStrategy(), patientId, data, timestamp, patientMap);
        }
    }

    private void checkForAlert(AlertStrategy alertStrategy, int patientId, double data, long timestamp, Map<Integer,
            Patient> patientMap){
        Alert alert = alertStrategy.checkAlert(patientId, data, timestamp, patientMap);
        if (alert != null) {
            alerts.put(alert.getAlertId(), alert);
            System.out.println("Alert added to the list");
        }
    }

    public static DataStorage getDataStorage() {
        if (dataStorage == null) {
            dataStorage = new DataStorage();
            return dataStorage;
        } else {
            return dataStorage;
        }
    }
}
