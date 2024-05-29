package com.data_management;

import com.alerts.*;
import com.alerts.objects.Alert;
import com.alerts.objects.SaturationAlert;
import com.data_management.records.Patient;
import com.data_management.records.PatientRecord;

import java.util.*;

/**
 * Manages storage and retrieval of patient data within a healthcare monitoring
 * system.
 * This class serves as a repository for all patient records, organized by
 * patient IDs.
 */
public class DataStorage {

    Map<Integer, Alert> alerts;
    AlertStrategy alertStrategy;
    private final Map<Integer, Patient> patientMap; // Stores patient objects indexed by their unique patient ID.

    /**
     * Constructs a new instance of DataStorage, initializing the underlying storage
     * structure.
     */
    public DataStorage() {
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
                checkForAlert(alertStrategy = new BloodSaturationStrategy(), patientId, data, timestamp, patientMap);
                break;
            case "ECG":
                checkForAlert(alertStrategy = new HeartRateStrategy(), patientId, data, timestamp, patientMap);
                break;
            case "Diastolic Pressure":
                checkForAlert(alertStrategy = new DiastolicPressureStrategy(), patientId, data, timestamp, patientMap);
                break;
            case "Systolic Pressure":
                checkForAlert(alertStrategy = new SystolicPressureStrategy(), patientId, data, timestamp, patientMap);
        }
    }

    /**
     * Retrieves a collection of all patients stored in the data storage.
     *
     * @return a list of all patients
     */
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    private void checkForAlert(AlertStrategy alertStrategy, int patientId, double data, long timestamp, Map<Integer,
            Patient> patientMap){
        alertStrategy.checkAlert(patientId, data, timestamp, patientMap);
    }
}
