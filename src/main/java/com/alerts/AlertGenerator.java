package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.ArrayList;
import java.util.List;
import static com.alerts.Checkers.*;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    private List<Alert> alerts;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.alerts = new ArrayList<>();
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered by a static method in the
     * Checkers class that takes our list of alerts
     *
     * @param patient   the patient data to evaluate for alert conditions
     * @param startTime the starting time from which we want to retrieve and analyze data
     * @param endTime   the ending time before which we want to retrieve and analyze data
     */
    public void evaluateData(Patient patient, long startTime, long endTime) {
        List<PatientRecord> records = dataStorage.getRecords(patient.getId(), startTime, endTime);

        // Check for Blood Pressure Data Alerts
        checkBloodPressureAlerts(patient, records, alerts);

        // Check for Blood Saturation Data Alerts
        checkSaturationAlerts(patient, records, alerts);

        // Check for Combined Alerts
        checkCombinedAlerts(patient, records, alerts);

        // Check for ECG Data Alerts
        checkECGAlerts(patient, records, alerts);
    }

    /**
     * Retrieves the list of generated alerts.
     *
     * @return the list of alerts
     */
    public List<Alert> getAlerts() {
        return alerts;
    }

}

